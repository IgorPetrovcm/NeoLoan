package com.igorpetrovcm.neoloan.calculator;

import com.igorpetrovcm.neoloan.calculator.model.LoanStatementRequestDTO;
import com.igorpetrovcm.neoloan.calculator.usecase.CreateLoanOffers;
import com.igorpetrovcm.neoloan.calculator.web.controller.CalculatorController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestClient;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

//@WebMvcTest(CalculatorController.class)
@SpringBootTest()
//@WebMvcTest(CalculatorController.class)
public class CalculatorControllerTest {
    @Autowired
    MockMvc mockMvc;

    final RestClient restClient = RestClient.create();

    @MockBean
    CreateLoanOffers createLoanOffers;

//    @ParameterizedTest
//    @MethodSource
    void whenCorrectValuesThenCreated(LoanStatementRequestDTO loanStatement){

    }
}
