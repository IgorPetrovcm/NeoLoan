package com.igorpetrovcm.neoloan.calculator.web;

import com.igorpetrovcm.neoloan.calculator.adapter.paymentcalculator.AnnuityPaymentCalculator;
import com.igorpetrovcm.neoloan.calculator.usecase.CreateLoanOffers;
import com.igorpetrovcm.neoloan.calculator.usecase.port.MonthlyPaymentCalculator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {
    @Bean
    public MonthlyPaymentCalculator getMonthlyPaymentCalculator(){
        return new AnnuityPaymentCalculator();
    }

    @Bean
    public CreateLoanOffers getCreatorLoanOffers(){
        return new CreateLoanOffers(getMonthlyPaymentCalculator());
    }
}
