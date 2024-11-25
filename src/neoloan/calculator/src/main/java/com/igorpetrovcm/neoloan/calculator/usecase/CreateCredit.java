package com.igorpetrovcm.neoloan.calculator.usecase;

import com.igorpetrovcm.neoloan.calculator.model.CreditDTO;
import com.igorpetrovcm.neoloan.calculator.model.ScoringDataDTO;
import com.igorpetrovcm.neoloan.calculator.usecase.port.MonthlyPaymentCalculator;
import com.igorpetrovcm.neoloan.calculator.usecase.port.OfferValuesCalculator;

import java.math.BigDecimal;

public class CreateCredit {
    private final MonthlyPaymentCalculator monthlyPaymentCalculator;
    private final OfferValuesCalculator offerValuesCalculator;

    public CreateCredit(
            MonthlyPaymentCalculator monthlyPaymentCalculator,
            OfferValuesCalculator offerValuesCalculator
    ){
        this.monthlyPaymentCalculator = monthlyPaymentCalculator;
        this.offerValuesCalculator = offerValuesCalculator;
    }

    public CreditDTO createCredit(ScoringDataDTO scoringData){
        CreditDTO credit = new CreditDTO();

        credit.setAmount(offerValuesCalculator.calculateAmount(
                scoringData.getIsInsuranceEnabled(),
                scoringData.getIsSalaryClient(),
                scoringData.getAmount()
        ));

        credit.setRate(offerValuesCalculator.calculateRate(
                scoringData.getIsInsuranceEnabled(),
                scoringData.getIsSalaryClient()
        ));

        credit.setMonthlyPayment(monthlyPaymentCalculator.calculating(
                scoringData.getTerm(),
                credit.getRate(),
                credit.getAmount()
        ));

        credit.setPsk(offerValuesCalculator.calculatePsk(
                credit.getAmount(),
                credit.getMonthlyPayment(),
                credit.getTerm()
        ));

        return new CreditDTO();
    }
}
