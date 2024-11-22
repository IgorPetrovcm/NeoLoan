package com.igorpetrovcm.neoloan.calculator.web.controller;

import java.util.List;

import com.igorpetrovcm.neoloan.calculator.api.CalculatorApi;
import com.igorpetrovcm.neoloan.calculator.model.LoanOfferDTO;
import com.igorpetrovcm.neoloan.calculator.usecase.CreateLoanOffers;
import com.igorpetrovcm.neoloan.calculator.model.LoanStatementRequestDTO;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CalculatorController implements CalculatorApi {
    private final CreateLoanOffers creatorLoanOffers;

    public CalculatorController(CreateLoanOffers creatorLoanOffers){
        this.creatorLoanOffers = creatorLoanOffers;
    }

    @Override
    public ResponseEntity<List<LoanOfferDTO>> calculatorOffersPost(LoanStatementRequestDTO loanStatement){
        return new ResponseEntity<>(
                creatorLoanOffers.createOffers(loanStatement),
                HttpStatus.CREATED
        );
    }
}
