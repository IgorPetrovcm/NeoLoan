package com.igorpetrovcm.neoloan.calculator.adapter.offercalculator;

import com.igorpetrovcm.neoloan.calculator.usecase.port.OfferValuesCalculator;
import org.springframework.beans.factory.annotation.Value;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

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
