package com.igorpetrovcm.neoloan.calculator.adapter.paymentcalculator;

import com.igorpetrovcm.neoloan.calculator.usecase.port.MonthlyPaymentCalculator;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class AnnuityPaymentCalculator implements MonthlyPaymentCalculator {
    @Override
    public BigDecimal calculating(int term, BigDecimal rate, BigDecimal amount) {
        BigDecimal monthlyPayment = new BigDecimal(0);
        monthlyPayment.setScale(2);

        BigDecimal monthlyRate = rate
                .divide(BigDecimal.valueOf(100), 4, RoundingMode.CEILING)
                .divide(BigDecimal.valueOf(12), 4, RoundingMode.CEILING);

        BigDecimal temp = monthlyRate.add(
                monthlyRate.divide(
                        monthlyRate.add(BigDecimal.valueOf(1))
                                .pow(term)
                                .subtract(BigDecimal.valueOf(1)),
                        RoundingMode.CEILING)
        );

        monthlyPayment = amount.multiply(temp);

        return monthlyPayment;
    }
}
