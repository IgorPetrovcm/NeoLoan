package com.igorpetrovcm.neoloan.calculator.web;

import com.igorpetrovcm.neoloan.calculator.model.LoanStatementRequestDTO;

public class Copying {
    public static LoanStatementRequestDTO copy(LoanStatementRequestDTO loanStatement){
        return new LoanStatementRequestDTO(
                loanStatement.getAmount(),
                loanStatement.getTerm(),
                loanStatement.getFirstName(),
                loanStatement.getLastName(),
                loanStatement.getMiddleName(),
                loanStatement.getEmail(),
                loanStatement.getBirthdate(),
                loanStatement.getPassportSeries(),
                loanStatement.getPassportNumber()
        );
    }
}
