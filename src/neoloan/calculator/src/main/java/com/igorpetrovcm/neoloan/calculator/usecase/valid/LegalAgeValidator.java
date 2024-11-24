package com.igorpetrovcm.neoloan.calculator.usecase.valid;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.Period;

public class LegalAgeValidator implements
        ConstraintValidator<LegalAgeConstraint, LocalDate> {

    @Override
    public boolean isValid(LocalDate date, ConstraintValidatorContext context){
        Period period = Period.between(date, LocalDate.now());

        return (period.getYears() - 18) >= 0;
    }
}
