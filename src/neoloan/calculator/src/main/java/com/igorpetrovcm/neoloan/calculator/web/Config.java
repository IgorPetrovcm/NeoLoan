package com.igorpetrovcm.neoloan.calculator.web;

import com.igorpetrovcm.neoloan.calculator.usecase.CreateLoanOffers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {
    @Bean
    public CreateLoanOffers getCreatorLoanOffers(){
        return new CreateLoanOffers();
    }
}
