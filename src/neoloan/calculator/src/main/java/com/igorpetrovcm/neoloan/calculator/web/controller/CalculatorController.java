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
import com.igorpetrovcm.neoloan.calculator.model.LoanStatementRequestDTO;

import com.igorpetrovcm.neoloan.calculator.usecase.CreateOffers;
import com.igorpetrovcm.neoloan.calculator.web.mapper.OfferMapper;
import com.igorpetrovcm.neoloan.calculator.web.mapper.StatementMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CalculatorController implements CalculatorApi {
    private final CreateCredit createCredit;
    private final CreateOffers createOffers;

    private final StatementMapper statementMapper;
    private final OfferMapper offerMapper;

    @Override
    public ResponseEntity<List<LoanOfferDTO>> calculatorOffersPost(LoanStatementRequestDTO loanStatement){
        log.info("[Request] to [/calculator/offers] : " + loanStatement.toString());
        Period periodFromBirth = Period.between(loanStatement.getBirthdate(), LocalDate.now());
        if (periodFromBirth.getYears() - 18 < 0){
            throw new IllegalArgumentException("date of birth");
        }

        Statement statement = statementMapper.toStatement(loanStatement);
        List<LoanOfferDTO> offers = offerMapper.toLoanOffers(createOffers.create(statement));

        log.info("[Response] : " + offers.toString());

        return new ResponseEntity<>(
                offers,
                HttpStatus.CREATED
        );
    }

    @Override
    public ResponseEntity<CreditDTO> calculatorCalcPost(ScoringDataDTO scoringData){
        log.info("[Request] to [/calculator/calc] : " + scoringData.toString());
        Period periodFromBirth = Period.between(scoringData.getBirthdate(), LocalDate.now());
        if (periodFromBirth.getYears() - 18 < 0){
            throw new IllegalArgumentException("date of birth");
        }

        Statement statement = statementMapper.toStatement(scoringData);
        CreditDTO credit = offerMapper.toCredit(createCredit.create(statement));

        log.info("[Response] : " + credit.toString());

        return new ResponseEntity<>(credit, HttpStatus.CREATED);
    }
}
