package com.spottrack.platform.equipment.interfaces.rest.resources;

import com.spottrack.platform.equipment.domain.model.valueobjects.EquipmentStatus;
import com.spottrack.platform.equipment.domain.model.valueobjects.ManufacturerId;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

public record EquipmentResource(
        String equipmentName,
        EquipmentStatus status,
        String model,
        String  manufacturerId,
        String purchaseCurrency,
        BigDecimal purchaseAmount

) {
}
