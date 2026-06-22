package com.spottrack.platform.monitoring.domain.model.entities;

import com.spottrack.platform.gym.domain.model.valueobjects.EquipmentId;
import com.spottrack.platform.monitoring.domain.model.valueobjects.ReportId;

import java.time.LocalTime;


public class Report {
    ReportId reportId;
    LocalTime detectionStamp;
    EquipmentId targetEquipmentId;

}
