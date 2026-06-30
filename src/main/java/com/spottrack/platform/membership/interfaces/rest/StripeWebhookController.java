package com.spottrack.platform.membership.interfaces.rest;

import com.spottrack.platform.membership.application.commandservices.PaymentCommandService;
import com.spottrack.platform.membership.domain.model.commands.ConfirmPaymentCommand;
import com.spottrack.platform.membership.domain.model.commands.FailPaymentCommand;
import com.spottrack.platform.membership.domain.model.valueobjects.PaymentId;
import com.spottrack.platform.shared.application.result.ApplicationError;
import com.spottrack.platform.shared.application.result.Result;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/v1/webhooks")
@Tag(name = "Webhooks", description = "Stripe webhook event receiver")
public class StripeWebhookController {

    private final PaymentCommandService paymentCommandService;

    @Value("${stripe.webhook-secret}")
    private String webhookSecret;

    public StripeWebhookController(PaymentCommandService paymentCommandService) {
        this.paymentCommandService = paymentCommandService;
    }

    @PostMapping("/stripe")
    public ResponseEntity<Void> handleStripeEvent(
            @RequestBody byte[] payload,
            @RequestHeader("Stripe-Signature") String sigHeader
    ) {
        Event event;
        try {
            event = Webhook.constructEvent(
                    new String(payload, StandardCharsets.UTF_8), sigHeader, webhookSecret
            );
        } catch (SignatureVerificationException e) {
            log.warn("Stripe webhook signature verification failed: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }

        switch (event.getType()) {
            case "checkout.session.completed" -> handleSessionCompleted(event);
            case "checkout.session.expired" -> handleSessionExpired(event);
            default -> log.debug("Unhandled Stripe event type: {}", event.getType());
        }

        return ResponseEntity.ok().build();
    }

    private void handleSessionCompleted(Event event) {
        var dataObject = event.getDataObjectDeserializer().getObject();
        if (dataObject.isEmpty()) {
            log.error("Could not deserialize Stripe session for event {}", event.getId());
            return;
        }

        var session = (Session) dataObject.get();
        var paymentIdStr = session.getMetadata() != null ? session.getMetadata().get("paymentId") : null;

        if (paymentIdStr == null) {
            log.error("Missing paymentId in metadata for Stripe session {}", session.getId());
            return;
        }

        try {
            var paymentId = new PaymentId(UUID.fromString(paymentIdStr));
            var command = new ConfirmPaymentCommand(paymentId, session.getId());
            var result = paymentCommandService.handle(command);

            if (result instanceof Result.Failure<?, ?> failure && failure.error() instanceof ApplicationError error) {
                log.error("Failed to confirm payment {}: {}", paymentIdStr, error.message());
            } else {
                log.info("Payment {} confirmed via Stripe session {}", paymentIdStr, session.getId());
            }
        } catch (IllegalArgumentException e) {
            log.error("Invalid paymentId UUID in Stripe session metadata: {}", paymentIdStr);
        }
    }

    private void handleSessionExpired(Event event) {
        var dataObject = event.getDataObjectDeserializer().getObject();
        if (dataObject.isEmpty()) {
            log.error("Could not deserialize Stripe session for event {}", event.getId());
            return;
        }

        var session = (Session) dataObject.get();
        var paymentIdStr = session.getMetadata() != null ? session.getMetadata().get("paymentId") : null;

        if (paymentIdStr == null) {
            log.warn("Missing paymentId in metadata for expired Stripe session {}", session.getId());
            return;
        }

        try {
            var paymentId = new PaymentId(UUID.fromString(paymentIdStr));
            var command = new FailPaymentCommand(paymentId);
            var result = paymentCommandService.handle(command);

            if (result instanceof Result.Failure<?, ?> failure && failure.error() instanceof ApplicationError error) {
                log.error("Failed to mark payment {} as failed: {}", paymentIdStr, error.message());
            } else {
                log.info("Payment {} marked as failed due to expired Stripe session {}", paymentIdStr, session.getId());
            }
        } catch (IllegalArgumentException e) {
            log.error("Invalid paymentId UUID in expired Stripe session metadata: {}", paymentIdStr);
        }
    }
}
