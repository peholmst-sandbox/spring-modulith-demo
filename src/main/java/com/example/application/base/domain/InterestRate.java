package com.example.application.base.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public final class InterestRate {
    private final BigDecimal interestRate;

    private static final int DEFAULT_SCALE = 2;
    private static final RoundingMode DEFAULT_ROUNDING = RoundingMode.HALF_EVEN;

    public InterestRate(BigDecimal interestRate) {
        if (interestRate.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("The interest rate must be greater than zero");
        }
        this.interestRate = interestRate.setScale(DEFAULT_SCALE, DEFAULT_ROUNDING);
    }

    public InterestRate(String interestRate) {
        this(new BigDecimal(interestRate));
    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public Money calculateInterest(Money amount) {
        return amount.multiply(interestRate);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        InterestRate that = (InterestRate) o;
        return Objects.equals(interestRate, that.interestRate);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(interestRate);
    }
}
