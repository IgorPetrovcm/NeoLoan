package com.igorpetrovcm.neoloan.calculator.adapter.offercalculator;

import com.igorpetrovcm.neoloan.calculator.model.EmploymentDTO;
import com.igorpetrovcm.neoloan.calculator.model.ScoringDataDTO;
import com.igorpetrovcm.neoloan.calculator.usecase.port.OfferValuesCalculator;
import org.springframework.beans.factory.annotation.Value;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import static com.igorpetrovcm.neoloan.calculator.model.EmploymentDTO.EmploymentStatusEnum.SELF_EMPLOYED;
import static com.igorpetrovcm.neoloan.calculator.model.EmploymentDTO.EmploymentStatusEnum.UNEMPLOYED;

public class ValuesBasedOffersCalculator implements OfferValuesCalculator {
    @Value("${offers-calculator.base-rate}")
    private BigDecimal baseRate;

    @Value("${offers-calculator.base-insurance-rate}")
    private volatile BigDecimal insuranceBaseRate;

    @Override
    public BigDecimal calculateRate(boolean isInsuranceEnabled, boolean isSalaryClient) {
        int rateAdditional = 0;

        if (isInsuranceEnabled){
            rateAdditional += 3;
        }

        if (isSalaryClient){
            rateAdditional += 1;
        }

        if (isInsuranceEnabled && isSalaryClient){
            rateAdditional = 1;
        }

        return baseRate.subtract(BigDecimal.valueOf(rateAdditional));
    }

    @Override
    public BigDecimal calculateRate(EmploymentDTO.EmploymentStatusEnum status){
        switch (status.getValue()) {
            case ("UNEMPLOYED"):
                return null;
            case ("SELF_EMPLOYED"):
                return BigDecimal.valueOf(2);
            case ("BUSINESS_OWNER"):
                return BigDecimal.valueOf(1);
            default:
                return BigDecimal.valueOf(0);
        }
    }

    @Override
    public BigDecimal calculateRate(EmploymentDTO.PositionEnum position){
        switch (position.getValue()){
            case ("MID_MANAGER"):
                return BigDecimal.valueOf(-2);
            case ("TOP_MANAGER"):
                return BigDecimal.valueOf(-3);
            default:
                return BigDecimal.valueOf(0);
        }
    }

    @Override
    public BigDecimal calculateRate(ScoringDataDTO.MaritalStatusEnum maritalStatus){
        switch (maritalStatus.getValue()){
            case ("MARRIED"):
                return BigDecimal.valueOf(-3);
            case ("DIVORCED"):
                return BigDecimal.valueOf(1);
            default:
                return BigDecimal.valueOf(0);
        }
    }

    @Override
    public BigDecimal calculateRate(ScoringDataDTO.GenderEnum gender, int years){
        switch (gender.getValue()){
            case "MALE":
                if (years > 30 && years < 55){
                    return BigDecimal.valueOf(-3);
                }
                return BigDecimal.valueOf(0);
            case "FEMALE":
                if (years > 32 && years < 60){
                    return BigDecimal.valueOf(-3);
                }
                return BigDecimal.valueOf(0);
            default:
                return BigDecimal.valueOf(7);
        }
    }

    @Override
    public BigDecimal calculateAmount(boolean isInsuranceEnabled,
                                      boolean isSalaryClient,
                                      BigDecimal requestedAmount)
    {
        BigDecimal newAmount = requestedAmount;

        if (isInsuranceEnabled && isSalaryClient){
            return newAmount;
        }

        if (isInsuranceEnabled){
            newAmount = newAmount.add(calculateInsurance(requestedAmount));
        }

        return newAmount;
    }

    @Override
    public BigDecimal calculatePsk(BigDecimal amount, BigDecimal monthlyPayment, int term){
        BigDecimal totalPayment = monthlyPayment.multiply(BigDecimal.valueOf(term));
        BigDecimal overPayment = totalPayment.subtract(amount);

        BigDecimal psk = overPayment
                .divide(amount, 10, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(12))
                .divide(BigDecimal.valueOf(term)
                        .divide(BigDecimal.valueOf(12), 10, RoundingMode.HALF_UP),
                10, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100));

        return psk.setScale(2, RoundingMode.HALF_UP);
    }

    private BigDecimal calculateInsurance(BigDecimal amount){
        BigDecimal insuranceProgressiveRate = insuranceBaseRate;
        BigDecimal totalInsurance = BigDecimal.valueOf(0);
        BigDecimal localAmount = amount;
        List<BigDecimal> amountProgressive = new ArrayList<>();

        int rangeCount = 0;

        for ( ; localAmount.compareTo(BigDecimal.valueOf(0)) > 0; rangeCount++){
            localAmount = localAmount.subtract(BigDecimal.valueOf(500000));
        }

        for (int i = 1; i < rangeCount; i++){
            amountProgressive.add(BigDecimal.valueOf(500000));
        }

        amountProgressive.add(BigDecimal.valueOf(500000).add(localAmount));

        for (int i = 0; i < rangeCount; i++){
            totalInsurance = totalInsurance.add(
                    amountProgressive.get(i)
                            .multiply(insuranceProgressiveRate)
                            .divide(BigDecimal.valueOf(100), RoundingMode.HALF_UP)
            );

            insuranceProgressiveRate = insuranceProgressiveRate.add(BigDecimal.valueOf(0.2));
        }

        return totalInsurance;
    }
}
