package com.example.converter;

import com.example.entity.BirthDate;
import jakarta.persistence.AttributeConverter;

import java.sql.Date;
import java.util.Optional;

public class BirthDateConverter implements AttributeConverter<BirthDate, java.sql.Date> {

    @Override
    public Date convertToDatabaseColumn(BirthDate birthDate) {
        return Optional.ofNullable(birthDate)
                .map(BirthDate::birthDate)
                .map(Date::valueOf)
                .orElse(null);
    }

    @Override
    public BirthDate convertToEntityAttribute(Date date) {
        return Optional.ofNullable(date)
                .map(Date::toLocalDate)
                .map(BirthDate::new)
                .orElse(null);
    }
}
