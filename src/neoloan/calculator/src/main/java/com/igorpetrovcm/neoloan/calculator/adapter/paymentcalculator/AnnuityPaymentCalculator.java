package com.igorpetrovcm.neoloan.calculator.adapter.paymentcalculator;

import com.igorpetrovcm.neoloan.calculator.usecase.port.MonthlyPaymentCalculator;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class AnnuityPaymentCalculator implements MonthlyPaymentCalculator {
    @Override
    public BigDecimal calculating(int term, BigDecimal rate, BigDecimal amount) {
        BigDecimal monthlyPayment;

        BigDecimal monthlyRate = rate
                .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP)
                .divide(BigDecimal.valueOf(12), 2, RoundingMode.HALF_UP);

        BigDecimal temp = monthlyRate.add(
                monthlyRate.divide(
                        monthlyRate.add(BigDecimal.valueOf(1))
                                .pow(term)
                                .subtract(BigDecimal.valueOf(1)),
                2, RoundingMode.HALF_UP)
        );

        monthlyPayment = amount.multiply(temp);

        return monthlyPayment;
    }
}
