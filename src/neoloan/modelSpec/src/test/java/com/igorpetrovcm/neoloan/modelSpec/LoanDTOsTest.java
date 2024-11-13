package com.igorpetrovcm.neoloan.modelSpec;

import com.igorpetrovcm.neoloan.model.LoanOfferDTO;
import com.igorpetrovcm.neoloan.model.LoanStatementRequestDTO;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import java.math.BigDecimal;
import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class LoanDTOsTest {
    private Validator validator;

    @BeforeEach
    public void setUpValidator(){
        ValidatorFactory validatorFactory = 
            Validation.buildDefaultValidatorFactory();

        validator = validatorFactory.getValidator();
    }
    
    @Test
    public void shouldConstraintViolationWhenAmountNotCorrect(){
        LoanOfferDTO loanOffer = LoanOfferDTO.builder()
            .totalAmount(BigDecimal.valueOf(10))
            .requestedAmount(BigDecimal.valueOf(10))
            .build();

        Set<ConstraintViolation<LoanOfferDTO>> violations = 
            validator.validate(loanOffer);
        
        for (ConstraintViolation violation : violations){
            System.out.println(violation.getPropertyPath() + " - " + violation.getMessage());
        }

        Assertions.assertFalse(violations.isEmpty());
    }

    @Test
    public void shouldConstraintViolationWhenNameNotCorrect(){
        LoanStatementRequestDTO loanStatement = LoanStatementRequestDTO.builder()
            .firstName("f")
            .lastName("f")
            .build();

        Set<ConstraintViolation<LoanStatementRequestDTO>> violations =
            validator.validate(loanStatement);

        for (ConstraintViolation<LoanStatementRequestDTO> violation : violations){
            System.out.println(violation.getPropertyPath() + " - " + violation.getMessage());
        }

        Assertions.assertFalse(violations.isEmpty());
    }

}
