package com.igorpetrovcm.neoloan.calculator.web.controller;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

import com.igorpetrovcm.neoloan.calculator.api.CalculatorApi;
import com.igorpetrovcm.neoloan.calculator.domain.Statement;
import com.igorpetrovcm.neoloan.calculator.model.CreditDTO;
import com.igorpetrovcm.neoloan.calculator.model.LoanOfferDTO;
import com.igorpetrovcm.neoloan.calculator.model.ScoringDataDTO;
import com.igorpetrovcm.neoloan.calculator.usecase.CreateCredit;
import com.igorpetrovcm.neoloan.calculator.usecase.CreateLoanOffers;
import com.igorpetrovcm.neoloan.calculator.model.LoanStatementRequestDTO;

import com.igorpetrovcm.neoloan.calculator.web.mapper.StatementMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CalculatorController implements CalculatorApi {
    private final CreateLoanOffers creatorLoanOffers;
    private final CreateCredit createCredit;

    private final StatementMapper statementMapper;

    @Override
    public ResponseEntity<List<LoanOfferDTO>> calculatorOffersPost(LoanStatementRequestDTO loanStatement){
        Period periodFromBirth = Period.between(loanStatement.getBirthdate(), LocalDate.now());
        if (periodFromBirth.getYears() - 18 < 0){
            throw new IllegalArgumentException("date of birth");
        }

        return new ResponseEntity<>(
                creatorLoanOffers.createOffers(loanStatement),
                HttpStatus.CREATED
        );
    }

    @Override
    public ResponseEntity<CreditDTO> calculatorCalcPost(ScoringDataDTO scoringData){
        Period periodFromBirth = Period.between(scoringData.getBirthdate(), LocalDate.now());
        if (periodFromBirth.getYears() - 18 < 0){
            throw new IllegalArgumentException("date of birth");
        }

        CreditDTO credit = createCredit.createCredit(scoringData);

        if (credit == null){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(credit, HttpStatus.CREATED);
    }
}
