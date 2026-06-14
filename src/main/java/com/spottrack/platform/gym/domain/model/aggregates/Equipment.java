package com.spottrack.platform.gym.domain.model.aggregates;

import com.spottrack.platform.gym.domain.model.commands.RegisterEquipment;
import com.spottrack.platform.gym.domain.model.entities.Manufacturer;
import com.spottrack.platform.gym.domain.model.valueobjects.EquipmentId;
import com.spottrack.platform.gym.domain.model.valueobjects.EquipmentStatus;
import com.spottrack.platform.gym.domain.model.valueobjects.ManufacturerId;
import com.spottrack.platform.gym.domain.model.valueobjects.ZoneId;
import com.spottrack.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import com.spottrack.platform.shared.domain.model.valueobjects.Money;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import org.springframework.cglib.core.Local;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
public class Equipment extends AbstractDomainAggregateRoot<Equipment> {

    private EquipmentId id;
    private EquipmentStatus status;

    private String equipmentName;
    private String model;
    private Money purchasePrice;

    private ManufacturerId manufacturerId;
    private LocalDate maintenanceThreshold;
    private ZoneId zoneId;


    public Equipment() {}

    public Equipment(EquipmentStatus status, String equipmentName, String model, ManufacturerId manufacturerId, BigDecimal amount, String currency, ZoneId zoneId) {
        this.id = new EquipmentId(UUID.randomUUID().toString());
        this.status = status;
        this.equipmentName = equipmentName;
        this.model = model;
        this.manufacturerId = manufacturerId;
        this.purchasePrice = new Money(amount, currency);
        /**
         * Now is the default date, an Admin must define the date manually
         */
        this.maintenanceThreshold = LocalDate.now();
        this.zoneId = zoneId;
    }

    public Equipment(RegisterEquipment command) {
        this.id = new EquipmentId(UUID.randomUUID().toString());
        this.status = command.status();
        this.equipmentName = command.equipmentName();
        this.model = command.model();
        this.manufacturerId = command.manufacturerId();
        this.zoneId = command.zoneId();
        this.purchasePrice = command.purchasePrice();
        this.maintenanceThreshold = LocalDate.now();
    }


    public void markEquipmentOutOfService(){
        this.status = EquipmentStatus.OUT_OF_SERVICE;
    }

    public void updateStatus(EquipmentStatus status){
        this.status = status;
    }

    public void relocateEquipment(ZoneId zoneId) {
        if (zoneId != null){
            this.zoneId = zoneId;
        } else {
            throw new IllegalArgumentException("zoneId must not be null");
        }
    }

    public void setMaintenanceThreshold(LocalDate date){
        this.maintenanceThreshold = date;
    }
}