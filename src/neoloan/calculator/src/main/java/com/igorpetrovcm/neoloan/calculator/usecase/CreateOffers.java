package com.igorpetrovcm.neoloan.calculator.usecase;

import com.igorpetrovcm.neoloan.calculator.domain.Offer;
import com.igorpetrovcm.neoloan.calculator.domain.Statement;
import com.igorpetrovcm.neoloan.calculator.usecase.port.OfferSettings;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class CreateOffers {
    private final OfferSettings offerSettings;

    public List<Offer> create(Statement statement){
        List<Offer> offers = new ArrayList<>();

        for (int i = 0; i < 4; i++){
            Offer offer = new Offer();

            statement.setIsInsuranceEnabled((i & 2) > 0);
            statement.setIsSalaryClient((i & 1) > 0);

            try{
                offerSettings
                        .setId(offer)
                        .setTerm(statement, offer)
                        .setRequestedAmount(statement, offer)
                        .setAmount(statement, offer)
                        .setRate(statement, offer)
                        .setMonthlyPayment(offer);
            }
            catch (Exception ex){
                ex.printStackTrace();
            }

            offers.add(offer);
        }

        return offers;
    }
}
