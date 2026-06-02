package com.spottrack.platform.equipment.domain.model.entities;

import com.spottrack.platform.equipment.domain.model.valueobjects.ManufacturerId;
import jakarta.persistence.Embeddable;
import lombok.Getter;

import java.util.UUID;

@Getter
@Embeddable
public class Manufacturer {
    private final ManufacturerId id;
    private String name;
    private String country;
    private String website;

    /**
     * Class is still missing the contactEmail, this one has to be a valueobject in shared-kernel
     */

    public Manufacturer(String name, String country, String website){
        this.name=name;
        this.country=country;
        this.website=website;
        this.id = new ManufacturerId( UUID.randomUUID().toString());
    }


}
