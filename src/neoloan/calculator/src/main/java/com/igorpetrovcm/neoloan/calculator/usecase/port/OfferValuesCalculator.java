package com.igorpetrovcm.neoloan.calculator.usecase.port;

import java.math.BigDecimal;

public interface OfferValuesCalculator {
    BigDecimal calculateRate(boolean isInsuranceEnabled, boolean isSalaryClient);
    BigDecimal calculateAmount(boolean isInsuranceEnabled,
                               boolean isSalaryClient,
                               BigDecimal requestedAmount);
    BigDecimal calculatePsk(BigDecimal amount, BigDecimal monthlyPayment, int term);
}
