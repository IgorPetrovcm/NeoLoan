package com.igorpetrovcm.neoloan.calculator.usecase;

import com.igorpetrovcm.neoloan.calculator.model.LoanOfferDTO;
import com.igorpetrovcm.neoloan.calculator.model.LoanStatementRequestDTO;
import com.igorpetrovcm.neoloan.calculator.usecase.port.MonthlyPaymentCalculator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CreateLoanOffers {
    private final MonthlyPaymentCalculator monthlyPaymentCalculator;

    public CreateLoanOffers(MonthlyPaymentCalculator monthlyPaymentCalculator){
        this.monthlyPaymentCalculator = monthlyPaymentCalculator;
    }

    public List<LoanOfferDTO> createOffers(LoanStatementRequestDTO loanStatement){
        List<LoanOfferDTO> offers = new ArrayList<>();

        for (int i = 0; i < 4; i++){
            LoanOfferDTO offer = new LoanOfferDTO();
            offer.setRate(BigDecimal.valueOf(10));
            offer.setTerm(loanStatement.getTerm());
            offer.setTotalAmount(loanStatement.getAmount());

            offer.setMonthlyPayment(monthlyPaymentCalculator.calculating(
                    offer.getTerm(),
                    offer.getRate(),
                    loanStatement.getAmount()
                ));

            offer.setStatementId(UUID.randomUUID());
            offer.setRequestedAmount(loanStatement.getAmount());

            offer.setIsInsuranceEnabled( (i & 2) > 0 ); // 2 = [0000 0000 0000 0010]
            offer.setIsSalaryClient( (i & 1) > 0 ); // 1 = [0000 0000 0000 0001]

            offers.add(offer);
        }

        return offers;
    }
}
