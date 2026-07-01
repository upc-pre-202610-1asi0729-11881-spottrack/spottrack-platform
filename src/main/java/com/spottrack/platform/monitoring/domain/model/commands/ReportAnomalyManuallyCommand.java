package com.spottrack.platform.monitoring.domain.model.commands;

public record ReportAnomalyManuallyCommand(String reservationId, String equipmentId, String zoneId, String anomalyDescription) {

}
