package com.spottrack.platform.gym.domain.model.aggregates;

import com.spottrack.platform.gym.domain.model.entities.Branch;
import com.spottrack.platform.gym.domain.model.valueobjects.EquipmentId;
import com.spottrack.platform.gym.domain.model.valueobjects.GymId;
import com.spottrack.platform.gym.domain.model.valueobjects.ZoneId;
import com.spottrack.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class Gym extends AbstractDomainAggregateRoot<Gym> {

    private List<Branch> branchList;
    private GymId id;
    private String name;


    protected Gym() {}

    public Gym(String name) {
        this.id = new GymId(UUID.randomUUID().toString());
        this.name = name;
        this.branchList = new ArrayList<Branch>();
    }

    public void  validateIfzoneExists(ZoneId zoneId){
        boolean found = false;
        for (int i = 0; i < branchList.size();i++){
            for (int j =0; j < branchList.get(i).getZoneList().size(); j++){
                var elementId = branchList.get(i).getZoneList().get(j).getId();
                if (zoneId.equals(elementId)){
                    found = true;
                    break;
                }
            }
        }
        if (found == false){
            throw new IllegalArgumentException("zoneId doesnt exist");
        }
    }
}
