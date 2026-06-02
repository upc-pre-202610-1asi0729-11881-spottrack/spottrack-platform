package com.spottrack.platform.equipment.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

public record RegisterEquipmentResource(
        @Schema
        String equipmentName,
        String status,
        String model,
        String manufacturerName,
        String manufacturerCountry,
        String manufacturerWebsite,
        BigDecimal amount,
        String currency
) {
}
