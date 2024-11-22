package com.igorpetrovcm.neoloan.calculator.web.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import com.igorpetrovcm.neoloan.calculator.api.CalculatorApi;
import com.igorpetrovcm.neoloan.calculator.usecase.CreateLoanOffers;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.igorpetrovcm.neoloan.calculator.model.LoanOfferDTO;
import com.igorpetrovcm.neoloan.calculator.model.LoanStatementRequestDTO;
//import org.springframework.web.bind.annotation.GetMapping;
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

//    @GetMapping("/create")
//    public ResponseEntity<LoanStatementRequestDTO> createTestRequestGet(){
//        LoanStatementRequestDTO loanStatement =
//                LoanStatementRequestDTO.builder()
//                        .amount(BigDecimal.valueOf(20000))
//                        .firstName("Hell")
//                        .lastName("Hell")
//                        .middleName(null)
//                        .email("ieefs33@mail.ru")
//                        .birthdate(LocalDate.of(2000, 10, 10))
//                        .passportSeries(BigDecimal.valueOf(1234))
//                        .passportNumber(BigDecimal.valueOf(123456))
//                        .build();
//
//        return ResponseEntity.ok(loanStatement);
//    }
}
