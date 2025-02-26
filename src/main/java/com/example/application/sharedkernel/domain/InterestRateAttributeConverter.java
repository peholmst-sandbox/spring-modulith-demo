package com.example.application.sharedkernel.domain;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.jspecify.annotations.Nullable;

import java.math.BigDecimal;

@Converter
public class InterestRateAttributeConverter implements AttributeConverter<InterestRate, BigDecimal> {

    @Override
    public @Nullable BigDecimal convertToDatabaseColumn(@Nullable InterestRate interestRate) {
        return interestRate == null ? null : interestRate.getInterestRate();
    }

    @Override
    public @Nullable InterestRate convertToEntityAttribute(@Nullable BigDecimal bigDecimal) {
        return bigDecimal == null ? null : new InterestRate(bigDecimal);
    }
}
