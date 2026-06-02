package com.spottrack.platform.equipment.interfaces.rest.transform;

import com.spottrack.platform.equipment.domain.model.commands.RegisterEquipment;
import com.spottrack.platform.equipment.interfaces.rest.resources.RegisterEquipmentResource;
import com.spottrack.platform.shared.domain.model.valueobjects.Money;

public class RegisterEquipmentCommandFromResourceAssembler {
    public static RegisterEquipment toCommandFromResource(RegisterEquipmentResource resource){
        return new RegisterEquipment(
                resource.equipmentName(),
                resource.status(),
                resource.model(),
                resource.manufacturerName(),
                resource.manufacturerCountry(),
                resource.manufacturerWebsite(),
                new Money(resource.purchaseAmount(), resource.purchaseCurrency())
        );
    }
}
