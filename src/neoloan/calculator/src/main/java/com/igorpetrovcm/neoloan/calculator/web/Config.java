package com.igorpetrovcm.neoloan.calculator.web;

import com.igorpetrovcm.neoloan.calculator.adapter.offercalculator.ValuesBasedOffersCalculator;
import com.igorpetrovcm.neoloan.calculator.adapter.paymentcalculator.AnnuityPaymentCalculator;
import com.igorpetrovcm.neoloan.calculator.usecase.CreateLoanOffers;
import com.igorpetrovcm.neoloan.calculator.usecase.port.MonthlyPaymentCalculator;
import com.igorpetrovcm.neoloan.calculator.usecase.port.OfferValuesCalculator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@Configuration
@PropertySources({
        @PropertySource("application.yaml")
})
public class Config {
    @Bean
    public MonthlyPaymentCalculator getMonthlyPaymentCalculator(){
        return new AnnuityPaymentCalculator();
    }

    @Bean
    public OfferValuesCalculator getOfferValuesCalculator(){
        return new ValuesBasedOffersCalculator();
    }

    @Bean
    public CreateLoanOffers getCreatorLoanOffers(){
        return new CreateLoanOffers(getMonthlyPaymentCalculator(), getOfferValuesCalculator());
    }
}
