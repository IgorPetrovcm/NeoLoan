package com.igorpetrovcm.neoloan.calculator.web.mapper;

import com.igorpetrovcm.neoloan.calculator.domain.Offer;
import com.igorpetrovcm.neoloan.calculator.model.CreditDTO;
import com.igorpetrovcm.neoloan.calculator.model.LoanOfferDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface OfferMapper {
    LoanOfferDTO toLoanOffer(Offer offer);
    CreditDTO toCredit(Offer offer);
}
