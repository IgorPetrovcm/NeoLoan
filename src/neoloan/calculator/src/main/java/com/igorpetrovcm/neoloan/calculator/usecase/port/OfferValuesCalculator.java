package com.igorpetrovcm.neoloan.calculator.usecase.port;

import com.igorpetrovcm.neoloan.calculator.model.EmploymentDTO;
import com.igorpetrovcm.neoloan.calculator.model.ScoringDataDTO;

import java.math.BigDecimal;

public interface OfferValuesCalculator {
    BigDecimal calculateRate(boolean isInsuranceEnabled, boolean isSalaryClient);
    BigDecimal calculateAmount(boolean isInsuranceEnabled,
                               boolean isSalaryClient,
                               BigDecimal requestedAmount);
    BigDecimal calculateRate(EmploymentDTO.EmploymentStatusEnum status);
    BigDecimal calculateRate(EmploymentDTO.PositionEnum position);
    BigDecimal calculateRate(ScoringDataDTO.MaritalStatusEnum maritalStatus);
    BigDecimal calculateRate(ScoringDataDTO.GenderEnum gender, int years);
    BigDecimal calculatePsk(BigDecimal amount, BigDecimal monthlyPayment, int term);
}
