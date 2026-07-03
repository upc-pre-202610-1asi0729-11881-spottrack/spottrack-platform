package com.spottrack.platform.gym.interfaces.events;

import java.time.LocalDate;

/**
 * Notification hook for the (future) Alerts/notifications context — deliberately
 * left unconsumed here, same as monitoring's AnomalyReportSubmittedIntegrationEvent.
 * Actual alert delivery is owned by another team; this event is only the signal.
 */
public record MaintenanceThresholdAlertIntegrationEvent(String equipmentId, LocalDate threshold) {}
