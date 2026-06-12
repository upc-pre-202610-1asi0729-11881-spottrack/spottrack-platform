package com.spottrack.platform.equipment.interfaces.rest.resources;

import com.spottrack.platform.equipment.domain.model.aggregates.Equipment;
import com.spottrack.platform.equipment.domain.model.valueobjects.EquipmentStatus;
import com.spottrack.platform.equipment.domain.model.valueobjects.ManufacturerId;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

public record RegisterEquipmentResource(
        @Schema
        String equipmentName,
        @Schema
        EquipmentStatus status,
        @Schema
        String model,
        @Schema
        String manufacturerId,
        @Schema
        String purchaseCurrency,
        @Schema
        BigDecimal purchaseAmount

) {
}
