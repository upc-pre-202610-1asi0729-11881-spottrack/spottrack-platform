package com.spottrack.platform.analytics.domain.model.aggregates;

import com.spottrack.platform.analytics.domain.model.events.*;
import com.spottrack.platform.analytics.domain.model.valueobjects.ROIProjectionId;
import com.spottrack.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "roi_projections")
public class ROIProjection extends AbstractDomainAggregateRoot<ROIProjection> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private ROIProjectionId roiProjectionId;

    private Double requestedDowntimeCost;
    private Double requestedEarnings;
    private Double roiIndex;
    private String demandStatus;

    protected ROIProjection() {
    }

    public ROIProjection(ROIProjectionId roiProjectionId) {
        this.roiProjectionId = roiProjectionId;
        this.requestedDowntimeCost = 0.0;
        this.requestedEarnings = 0.0;
        this.roiIndex = 0.0;
        this.demandStatus = "NORMAL";
    }

    public void updateDowntimeCost(Double cost) {
        this.requestedDowntimeCost = cost;
        this.registerEvent(new RequestedDowntimeCostEvent(this.roiProjectionId, cost));
    }

    public void updateEarnings(Double earnings) {
        this.requestedEarnings = earnings;
        this.registerEvent(new RequestedEarningsEvent(this.roiProjectionId, earnings));
    }

    public void generateROIProjection(Double roiIndex) {
        this.roiIndex = roiIndex;
        this.registerEvent(new ROIProjectionGeneratedEvent(this.roiProjectionId, roiIndex));
    }

    public void markAsLowDemand(String slotInfo) {
        this.demandStatus = "LOW";
        this.registerEvent(new LowDemandSlotDetectedEvent(this.roiProjectionId, slotInfo));
    }

    public void markAsHighDemand(String slotInfo) {
        this.demandStatus = "HIGH";
        this.registerEvent(new HighDemandSlotDetectedEvent(this.roiProjectionId, slotInfo));
    }

    public void recommendEquipmentTransfer(String detail) {
        this.registerEvent(new EquipmentTransferRecommendedEvent(this.roiProjectionId, detail));
    }
}
