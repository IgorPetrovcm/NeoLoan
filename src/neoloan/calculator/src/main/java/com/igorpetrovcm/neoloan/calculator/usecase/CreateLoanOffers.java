package com.igorpetrovcm.neoloan.calculator.usecase;

import com.igorpetrovcm.neoloan.calculator.model.LoanOfferDTO;
import com.igorpetrovcm.neoloan.calculator.model.LoanStatementRequestDTO;
import com.igorpetrovcm.neoloan.calculator.usecase.port.MonthlyPaymentCalculator;
import com.igorpetrovcm.neoloan.calculator.usecase.port.OfferSettings;
import com.igorpetrovcm.neoloan.calculator.usecase.port.OfferValuesCalculator;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class CreateLoanOffers {
    private final MonthlyPaymentCalculator monthlyPaymentCalculator;
    private final OfferValuesCalculator offerValuesCalculator;
    private final OfferSettings offerSettings;

    private final OfferRateComparator offerRateComparator = new OfferRateComparator();

    public List<LoanOfferDTO> createOffers(LoanStatementRequestDTO loanStatement){
        List<LoanOfferDTO> offers = new ArrayList<>();

        for (int i = 0; i < 4; i++){
            LoanOfferDTO offer = new LoanOfferDTO();

//            offer.setTerm(loanStatement.getTerm());
//            offer.setStatementId(UUID.randomUUID());
//            offer.setRequestedAmount(loanStatement.getAmount());
//
//            offer.setIsInsuranceEnabled( (i & 2) > 0 ); // 2 = [0000 0000 0000 0010]
//            offer.setIsSalaryClient( (i & 1) > 0 ); // 1 = [0000 0000 0000 0001]
//
//            offer.setRate(offerValuesCalculator.calculateRate(
//                            offer.getIsInsuranceEnabled(),
//                            offer.getIsSalaryClient()
//            ));
//
//            offer.setTotalAmount(offerValuesCalculator.calculateAmount(
//                    offer.getIsInsuranceEnabled(),
//                    offer.getIsSalaryClient(),
//                    loanStatement.getAmount()
//            ));
//
//            offer.setMonthlyPayment(monthlyPaymentCalculator.calculating(
//                    offer.getTerm(),
//                    offer.getRate(),
//                    loanStatement.getAmount()
//                ));

            offers.add(offer);
        }

        return offers.stream()
                .sorted(offerRateComparator)
                .collect(Collectors.toList());
    }

    static class OfferRateComparator implements Comparator<LoanOfferDTO>{
        @Override
        public int compare(LoanOfferDTO offer1, LoanOfferDTO offer2){
            return offer1.getRate().compareTo(offer2.getRate());
        }
    }
}
