package com.spottrack.platform.monitoring.domain.model.commands;

import com.spottrack.platform.monitoring.domain.model.entities.Report;
import com.spottrack.platform.monitoring.domain.model.valueobjects.*;

public record ReportAnomalyManuallyCommand(ReportId reportId, ZoneId zoneId, EquipmentId equipmetId, String description, AnomalyType anomalyType, DamageType damageType) {

}
