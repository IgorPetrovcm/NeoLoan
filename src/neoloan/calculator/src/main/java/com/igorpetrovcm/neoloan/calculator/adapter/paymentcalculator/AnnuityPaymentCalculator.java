package com.igorpetrovcm.neoloan.calculator.adapter.paymentcalculator;

import com.igorpetrovcm.neoloan.calculator.usecase.port.MonthlyPaymentCalculator;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class AnnuityPaymentCalculator implements MonthlyPaymentCalculator {
    @Override
    public BigDecimal calculating(int term, BigDecimal rate, BigDecimal amount) {
        BigDecimal monthlyPayment;

        BigDecimal monthlyRate = rate
                .divide(BigDecimal.valueOf(100), 10, RoundingMode.HALF_UP)
                .divide(BigDecimal.valueOf(12), 10, RoundingMode.HALF_UP);

        monthlyPayment = amount
                .multiply(monthlyRate)
                .multiply(monthlyRate.add(BigDecimal.valueOf(1))
                        .pow(term))
                .divide(monthlyRate.add(BigDecimal.valueOf(1))
                        .pow(term)
                        .subtract(BigDecimal.valueOf(1)),3, RoundingMode.HALF_UP
                    );

        return monthlyPayment;
    }
}
