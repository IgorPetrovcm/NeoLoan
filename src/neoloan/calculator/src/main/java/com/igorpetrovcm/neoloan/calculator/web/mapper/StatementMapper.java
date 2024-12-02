package com.igorpetrovcm.neoloan.calculator.web.mapper;

import com.igorpetrovcm.neoloan.calculator.domain.Statement;
import com.igorpetrovcm.neoloan.calculator.model.LoanStatementRequestDTO;
import com.igorpetrovcm.neoloan.calculator.model.ScoringDataDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface StatementMapper {
    Statement toStatement(LoanStatementRequestDTO loanStatement);
    Statement toStatement(ScoringDataDTO scoringData);
}
