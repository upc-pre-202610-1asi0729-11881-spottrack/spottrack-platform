package com.spottrack.platform.reservation.domain.model.valueobjects;

public record ClientId (String uuid){
    public ClientId{
        if (uuid == null ||uuid.isBlank()){
            throw new IllegalArgumentException("Client id cannot be null or less than 1");
        }
    }
}
