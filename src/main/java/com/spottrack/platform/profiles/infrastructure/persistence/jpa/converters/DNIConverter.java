package com.spottrack.platform.profiles.infrastructure.persistence.jpa.converters;

import com.spottrack.platform.profiles.domain.model.valueobjects.DNI;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class DNIConverter implements AttributeConverter<DNI, String> {

    @Override
    public String convertToDatabaseColumn(DNI attribute) {
        return attribute == null ? null : attribute.dni();
    }

    @Override
    public DNI convertToEntityAttribute(String dbData) {
        return dbData == null ? null : new DNI(dbData);
    }
}
