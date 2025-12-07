package com.pece.agencia.api.common.hibernate;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.time.Period;

@Converter(autoApply = true)
public class PeriodToDaysConverter implements AttributeConverter<Period, Integer> {
    @Override
    public Integer convertToDatabaseColumn(Period period) {
        if (period == null) {
            return null;
        } else {
            return period.getDays();
        }
    }

    @Override
    public Period convertToEntityAttribute(Integer days) {
        if (days == null) {
            return null;
        } else {
            return Period.ofDays(days);
        }
    }
}
