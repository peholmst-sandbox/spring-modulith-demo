package com.example.application.base.domain;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.jspecify.annotations.Nullable;

import java.math.BigDecimal;

@Converter
public class MoneyAttributeConverter implements AttributeConverter<Money, BigDecimal> {

    @Override
    public @Nullable BigDecimal convertToDatabaseColumn(@Nullable Money money) {
        return money == null ? null : money.getAmount();
    }

    @Override
    public @Nullable Money convertToEntityAttribute(@Nullable BigDecimal bigDecimal) {
        return bigDecimal == null ? null : new Money(bigDecimal);
    }
}
