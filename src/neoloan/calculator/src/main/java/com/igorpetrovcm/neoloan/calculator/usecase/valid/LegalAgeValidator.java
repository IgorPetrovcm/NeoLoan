package com.igorpetrovcm.neoloan.calculator.usecase.valid;

import com.igorpetrovcm.neoloan.calculator.model.LoanOfferDTO;
import com.igorpetrovcm.neoloan.calculator.model.LoanStatementRequestDTO;
import jakarta.validation.*;

import java.time.LocalDate;
import java.time.Period;

public class LegalAgeValidator implements
    ConstraintValidator<LegalAgeConstraint, LoanStatementRequestDTO>{

    @Override
    public boolean isValid(LoanStatementRequestDTO statement, ConstraintValidatorContext context){
        Period period = Period.between(statement.getBirthdate(), LocalDate.now());

        return (period.getYears() - 18) >= 0;
    }
}
