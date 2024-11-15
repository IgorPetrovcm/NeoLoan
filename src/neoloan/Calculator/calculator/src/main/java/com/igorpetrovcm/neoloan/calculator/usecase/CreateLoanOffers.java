package com.igorpetrovcm.neoloan.calculator.usecase;

import com.igorpetrovcm.neoloan.model.LoanOfferDTO;
import com.igorpetrovcm.neoloan.model.LoanStatementRequestDTO;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CreateLoanOffers {
    public List<LoanOfferDTO> createOffers(LoanStatementRequestDTO loanStatement){
        List<LoanOfferDTO> offers = new ArrayList<LoanOfferDTO>(4);

        boolean condition = false;

        for (int i = 0; i < 4; i++){
            LoanOfferDTO offer = new LoanOfferDTO();
            offer.setRate(BigDecimal.valueOf(10));
            offer.setTerm(loanStatement.getTerm());
            offer.setTotalAmount(loanStatement.getAmount());

            BigDecimal monthlyRate = offer.getRate()
                    .divide(BigDecimal.valueOf(100), 10, RoundingMode.HALF_UP)
                    .divide(BigDecimal.valueOf(12), 10, RoundingMode.HALF_UP);

            offer.setMonthlyPayment(
                    offer.getTotalAmount()
                            .multiply(monthlyRate)
                            .multiply(monthlyRate.add(BigDecimal.valueOf(1))
                                    .pow(offer.getTerm()))
                            .divide(monthlyRate.add(BigDecimal.valueOf(1))
                                .pow(offer.getTerm())
                                    .subtract(BigDecimal.valueOf(1)), 3, RoundingMode.HALF_UP)
            );

            offer.setStatementId(UUID.randomUUID());
            offer.setRequestedAmount(loanStatement.getAmount());

            if (i < 2){
                offer.setIsInsuranceEnabled(!condition);
                offer.setIsSalaryClient(true);
            }
            else {
                offer.setIsInsuranceEnabled(false);
                offer.setIsSalaryClient(!condition);
            }

            condition = !condition;

            offers.add(offer);
        }

        return offers;
    }
}
