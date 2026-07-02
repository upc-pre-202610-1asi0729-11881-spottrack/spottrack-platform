package com.spottrack.platform.analytics.domain.model.aggregates;

import com.spottrack.platform.analytics.domain.model.events.*;
import com.spottrack.platform.analytics.domain.model.valueobjects.MaintenanceQuoteId;
import com.spottrack.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import lombok.Getter;
import lombok.Setter;

@Getter
public class MaintenanceQuote extends AbstractDomainAggregateRoot<MaintenanceQuote> {

    @Setter
    private Long id;

    private MaintenanceQuoteId maintenanceQuoteId;

    private Double correctiveActionsCost;
    private Double sparePartsCost;
    private Double preventiveCost;
    private Double totalMaintenanceCost;

    public MaintenanceQuote() {
    }

    public MaintenanceQuote(MaintenanceQuoteId maintenanceQuoteId) {
        this.maintenanceQuoteId = maintenanceQuoteId;
        this.correctiveActionsCost = 0.0;
        this.sparePartsCost = 0.0;
        this.preventiveCost = 0.0;
        this.totalMaintenanceCost = 0.0;
    }

    public MaintenanceQuote(Long id, MaintenanceQuoteId maintenanceQuoteId, Double correctiveActionsCost,
                             Double sparePartsCost, Double preventiveCost, Double totalMaintenanceCost) {
        this.id = id;
        this.maintenanceQuoteId = maintenanceQuoteId;
        this.correctiveActionsCost = correctiveActionsCost;
        this.sparePartsCost = sparePartsCost;
        this.preventiveCost = preventiveCost;
        this.totalMaintenanceCost = totalMaintenanceCost;
    }

    public void updateCorrectiveActionsCost(Double cost) {
        this.correctiveActionsCost = cost;
        this.registerDomainEvent(new CostOfTheCorrectiveActionsRequestedEvent(this.maintenanceQuoteId, cost));
    }

    public void updateSparePartsCost(Double cost) {
        this.sparePartsCost = cost;
        this.registerDomainEvent(new CostOfSparePartsRequestedEvent(this.maintenanceQuoteId, cost));
    }

    public void updatePreventiveCost(Double cost) {
        this.preventiveCost = cost;
        this.registerDomainEvent(new PreventiveCostRequestedEvent(this.maintenanceQuoteId, cost));
    }

    public void calculateTotalMaintenanceCost(Double totalCost) {
        this.totalMaintenanceCost = totalCost;
        this.registerDomainEvent(new MaintenanceCostRequestedEvent(this.maintenanceQuoteId, totalCost));
    }
}
