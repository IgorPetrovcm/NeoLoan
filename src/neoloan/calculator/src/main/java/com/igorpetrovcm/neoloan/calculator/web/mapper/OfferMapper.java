package com.igorpetrovcm.neoloan.calculator.web.mapper;

import com.igorpetrovcm.neoloan.calculator.domain.Offer;
import com.igorpetrovcm.neoloan.calculator.model.CreditDTO;
import com.igorpetrovcm.neoloan.calculator.model.LoanOfferDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface OfferMapper {
    LoanOfferDTO toLoanOffer(Offer offer);

    @Mapping(target = "amount", source = "totalAmount")
    CreditDTO toCredit(Offer offer);

    List<LoanOfferDTO> toLoanOffers(List<Offer> offers);
}
