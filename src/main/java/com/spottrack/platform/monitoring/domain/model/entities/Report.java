package com.spottrack.platform.monitoring.domain.model.entities;

import com.spottrack.platform.monitoring.domain.model.valueobjects.EquipmentId;
import com.spottrack.platform.monitoring.domain.model.valueobjects.ReportId;
import com.spottrack.platform.monitoring.domain.model.valueobjects.ZoneId;

import java.time.LocalTime;


public class Report {
    ReportId reportId;
    LocalTime detectionStamp;
    EquipmentId targetEquipmentId;
    ZoneId zoneId;
}
