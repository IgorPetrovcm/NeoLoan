package com.igorpetrovcm.neoloan.calculator.usecase.port;

import java.math.BigDecimal;

public interface RateCalculator {
    BigDecimal calculating(boolean isSalaryClient, boolean isInsuranceEnabled);
}
