package com.igorpetrovcm.neoloan.calculator.usecase.port;

import java.math.BigDecimal;

public interface MonthlyPaymentCalculator {
    BigDecimal calculating(int term, BigDecimal rate, BigDecimal amount);
}
