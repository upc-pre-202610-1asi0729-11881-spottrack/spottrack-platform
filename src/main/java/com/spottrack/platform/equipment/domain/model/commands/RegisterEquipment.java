package com.spottrack.platform.equipment.domain.model.commands;

import com.spottrack.platform.equipment.domain.model.valueobjects.EquipmentStatus;
import com.spottrack.platform.shared.domain.model.valueobjects.Money;

import java.math.BigDecimal;

public record RegisterEquipment(
        String equipmentName,
        EquipmentStatus status,
        String model,
        String manufacturerName,
        String manufacturerCountry,
        String manufacturerWebsite,
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
        if (manufacturerName == null || manufacturerName.isBlank()) {
            throw new IllegalArgumentException("equipment.command.registerEquipment.manufacturerName.notBlank");
        }
        if (manufacturerCountry == null || manufacturerCountry.isBlank()) {
            throw new IllegalArgumentException("equipment.command.registerEquipment.manufacturerCountry.notBlank");
        }
        if (purchasePrice == null) {
            throw new IllegalArgumentException("equipment.command.registerEquipment.purchaseAmount.notNull");
        }
        if (purchasePrice.amount().equals(BigDecimal.ZERO)) {
            throw new IllegalArgumentException("equipment.command.registerEquipment.purchaseAmount.notZero");
        }
    }
}
