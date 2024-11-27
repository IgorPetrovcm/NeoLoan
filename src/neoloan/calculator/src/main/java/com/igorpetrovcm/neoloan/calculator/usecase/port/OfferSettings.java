package com.igorpetrovcm.neoloan.calculator.usecase.port;

import com.igorpetrovcm.neoloan.calculator.domain.Offer;
import com.igorpetrovcm.neoloan.calculator.domain.Statement;

public interface OfferSettings {
    OfferSettings validateValues(Statement statement);
    OfferSettings setId(Offer offer);
    OfferSettings setTerm(Statement statement, Offer offer);
    OfferSettings setRequestedAmount(Statement statement, Offer offer);
    OfferSettings setAmount(Statement statement, Offer offer);
    OfferSettings setRate(Statement statement, Offer offer);
    OfferSettings setMonthlyPayment(Offer offer);
    OfferSettings setPaymentScheduleElements(Offer offer);
    OfferSettings setPsk(Offer offer);
}
