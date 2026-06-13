package com.spottrack.platform.equipment.domain.model.commands;

import com.spottrack.platform.equipment.domain.model.valueobjects.EquipmentStatus;
import com.spottrack.platform.equipment.domain.model.valueobjects.ManufacturerId;
import com.spottrack.platform.equipment.domain.model.valueobjects.ZoneId;
import com.spottrack.platform.shared.domain.model.valueobjects.Money;

import java.math.BigDecimal;

public record RegisterEquipment(
        String equipmentName,
        EquipmentStatus status,
        String model,
        ManufacturerId manufacturerId,
        ZoneId zoneId,
        Money purchasePrice
) {
    public RegisterEquipment {
        if (equipmentName == null || equipmentName.isBlank()) {
            throw new IllegalArgumentException("equipment.command.registerEquipment.equipmentName.notBlank");
        }
        if (model == null || model.isBlank()) {
            throw new IllegalArgumentException("equipment.command.registerEquipment.model.notBlank");
        }
        if (status == null) {
            throw new IllegalArgumentException("equipment.command.registerEquipment.status.notNull");
        }
        if (manufacturerId == null) {
            throw new IllegalArgumentException("manufacturer id must not be null");
        }
        if (zoneId == null) {
            throw new IllegalArgumentException("zone id must not be null");
        }
        if (purchasePrice == null) {
            throw new IllegalArgumentException("equipment.command.registerEquipment.purchaseAmount.notNull");
        }
        if (purchasePrice.amount().equals(BigDecimal.ZERO)) {
            throw new IllegalArgumentException("equipment.command.registerEquipment.purchaseAmount.notZero");
        }
    }
}
