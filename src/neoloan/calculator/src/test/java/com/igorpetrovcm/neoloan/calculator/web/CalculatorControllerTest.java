package com.igorpetrovcm.neoloan.calculator.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.igorpetrovcm.neoloan.calculator.model.LoanStatementRequestDTO;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.stream.Stream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class CalculatorControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objMapper;

    private static LoanStatementRequestDTO mainLoanStatement =
            new LoanStatementRequestDTO(
                        BigDecimal.valueOf(200000),
                        50,
                        "Testfirst",
                        "Testslast",
                        "Testmiddle",
                        "testmail@mail.te",
                        LocalDate.of(2000, 10,10),
                        "1234", "123456"
                    );

    @ParameterizedTest
    @MethodSource({"provideCorrectValues", "provideNullMiddleName"})
    void whenCorrectValuesThenCreated(LoanStatementRequestDTO loanStatement) throws Exception {
        mockMvc.perform(post("/calculator/offers")
                .contentType("application/json")
                .content(objMapper.writeValueAsString(loanStatement)))
                .andExpect(status().isCreated()).andDo(print());
    }

    @ParameterizedTest
    @MethodSource({"provideNotCorrectNames", "provideNotCorrectAmount"})
    void whenNotCorrectValuesThenBadRequest(LoanStatementRequestDTO loanStatement) throws Exception{
        mockMvc.perform(post("/calculator/offers")
                .contentType("application/json")
                .content(objMapper.writeValueAsString(loanStatement))
                ).andExpect(status().isBadRequest())
                    .andDo(print());
    }

    private static Stream<Arguments> provideNotCorrectBirthDate(){
        LoanStatementRequestDTO statement1 = Copying.copy(mainLoanStatement);
        statement1.setBirthdate(LocalDate.of(5000, 4, 4));
        LoanStatementRequestDTO statement2 = Copying.copy(mainLoanStatement);
        statement2.setBirthdate(LocalDate.of(100, 12, 12));
        LoanStatementRequestDTO statement3 = Copying.copy(mainLoanStatement);
        statement3.setBirthdate(LocalDate.now());
        LoanStatementRequestDTO statement4 = Copying.copy(mainLoanStatement);
        statement4.setBirthdate(LocalDate.parse("10-30-2006"));

        return Stream.of(
                Arguments.of(statement1),
                Arguments.of(statement2),
                Arguments.of(statement3)
        );
    }

    private static Stream<Arguments> provideNotCorrectAmount(){
        LoanStatementRequestDTO statement1 = Copying.copy(mainLoanStatement);
        statement1.setAmount(BigDecimal.valueOf(1000));
        LoanStatementRequestDTO statement2 = Copying.copy(mainLoanStatement);
        statement2.setAmount(null);
        LoanStatementRequestDTO statement3 = Copying.copy(mainLoanStatement);
        statement3.setAmount(BigDecimal.valueOf(Integer.MAX_VALUE + 1));

        return Stream.of(
                Arguments.of(statement1),
                Arguments.of(statement2),
                Arguments.of(statement3)
        );
    }

    private static Stream<Arguments> provideNotCorrectNames(){
        LoanStatementRequestDTO statement1 = Copying.copy(mainLoanStatement);
        statement1.setLastName("");
        LoanStatementRequestDTO statement12 = Copying.copy(mainLoanStatement);
        statement12.setLastName("f");
        LoanStatementRequestDTO statement13 = Copying.copy(mainLoanStatement);
        statement13.setLastName(null);
        LoanStatementRequestDTO statement14 = Copying.copy(mainLoanStatement);
        statement14.setLastName("eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee" +
                "eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee" +
                "eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");

        LoanStatementRequestDTO statement2 = Copying.copy(mainLoanStatement);
        statement2.setFirstName("");
        LoanStatementRequestDTO statement22 = Copying.copy(mainLoanStatement);
        statement22.setFirstName("f");
        LoanStatementRequestDTO statement23 = Copying.copy(mainLoanStatement);
        statement23.setFirstName(null);
        LoanStatementRequestDTO statement24 = Copying.copy(mainLoanStatement);
        statement24.setLastName("eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee" +
                "eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee" +
                "eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");

        LoanStatementRequestDTO statement3 = Copying.copy(mainLoanStatement);
        statement3.setFirstName("");
        LoanStatementRequestDTO statement32 = Copying.copy(mainLoanStatement);
        statement32.setFirstName("f");
        LoanStatementRequestDTO statement33 = Copying.copy(mainLoanStatement);
        statement33.setFirstName(null);
        LoanStatementRequestDTO statement34 = Copying.copy(mainLoanStatement);
        statement34.setLastName("eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee" +
                "eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee" +
                "eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");

        return Stream.of(
                Arguments.of(statement1),
                Arguments.of(statement12),
                Arguments.of(statement13),
                Arguments.of(statement14),
                Arguments.of(statement2),
                Arguments.of(statement22),
                Arguments.of(statement23),
                Arguments.of(statement24),
                Arguments.of(statement3),
                Arguments.of(statement32),
                Arguments.of(statement33),
                Arguments.of(statement34)
        );
    }

    private static Stream<Arguments> provideNullMiddleName(){
        LoanStatementRequestDTO statement = Copying.copy(mainLoanStatement);
        statement.setMiddleName(null);

        return Stream.of(Arguments.of(statement));
    }

    private static Stream<Arguments> provideCorrectValues(){
        LoanStatementRequestDTO statement2 = Copying.copy(mainLoanStatement);
        statement2.setAmount(BigDecimal.valueOf(1400000000));
        statement2.setBirthdate(LocalDate.of(1922, 10,10));

        return Stream.of(Arguments.of(mainLoanStatement), Arguments.of(statement2));
    }
}
