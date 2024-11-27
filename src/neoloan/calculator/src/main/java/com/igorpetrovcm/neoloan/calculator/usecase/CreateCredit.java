package com.igorpetrovcm.neoloan.calculator.usecase;

import com.igorpetrovcm.neoloan.calculator.domain.Offer;
import com.igorpetrovcm.neoloan.calculator.domain.Statement;
import com.igorpetrovcm.neoloan.calculator.usecase.port.MonthlyPaymentCalculator;
import com.igorpetrovcm.neoloan.calculator.usecase.port.OfferSettings;
import com.igorpetrovcm.neoloan.calculator.usecase.port.OfferValuesCalculator;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CreateCredit {
    private final MonthlyPaymentCalculator monthlyPaymentCalculator;
    private final OfferValuesCalculator offerValuesCalculator;
    private final OfferSettings offerSettings;

    public Offer create(Statement statement){
        Offer offer = new Offer();

        try{
            offerSettings
                    .setRequestedAmount(statement, offer)
                    .setTerm(statement, offer)
                    .setAmount(statement, offer)
                    .setRate(statement, offer)
                    .setMonthlyPayment(offer)
                    .setPaymentScheduleElements(offer)
                    .setPsk(offer);
        }
        catch (Exception ex){
            ex.printStackTrace();
        }

        return offer;
    }
}
