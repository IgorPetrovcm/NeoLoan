package com.igorpetrovcm.neoloan.calculator.usecase;

import com.igorpetrovcm.neoloan.calculator.model.CreditDTO;
import com.igorpetrovcm.neoloan.calculator.model.ScoringDataDTO;
import com.igorpetrovcm.neoloan.calculator.usecase.port.MonthlyPaymentCalculator;
import com.igorpetrovcm.neoloan.calculator.usecase.port.OfferValuesCalculator;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;

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

        credit.setRate(credit.getRate()
                .add(offerValuesCalculator.calculateRate(
                                scoringData.getEmployment().getEmploymentStatus()
                        )));
        if (credit.getRate() == null){
            return null;
        }

        credit.setRate(credit.getRate()
                .add(offerValuesCalculator.calculateRate(
                        scoringData.getEmployment().getPosition()
                )));

        credit.setRate(credit.getRate()
                .add(offerValuesCalculator.calculateRate(
                        scoringData.getMaritalStatus()
                )));

        if (scoringData.getAmount().compareTo(
                scoringData.getEmployment().getSalary()
                        .multiply(BigDecimal.valueOf(24))) > 0){
            return null;
        }

        Period birthPeriod = Period.between(scoringData.getBirthdate(), LocalDate.now());
        if (birthPeriod.getYears() < 20 || birthPeriod.getYears() > 65){
            return null;
        }

        credit.setRate(credit.getRate()
                .add(offerValuesCalculator.calculateRate(
                        scoringData.getGender(), birthPeriod.getYears()
                )));

        if (scoringData.getEmployment().getWorkExperienceTotal() < 18){
            return null;
        }
        if (scoringData.getEmployment().getWorkExperienceCurrent() < 3){
            return null;
        }

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
