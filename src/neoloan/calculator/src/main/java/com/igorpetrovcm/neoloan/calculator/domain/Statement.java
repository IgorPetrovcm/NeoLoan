package com.igorpetrovcm.neoloan.calculator.domain;

import com.igorpetrovcm.neoloan.calculator.model.EmploymentDTO;
import com.igorpetrovcm.neoloan.calculator.model.ScoringDataDTO;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Statement {
    private BigDecimal amount;

    private Integer term;

    private String firstName;

    private String lastName;

    private String middleName;

    public enum GenderEnum {
        MALE("MALE"),

        FEMALE("FEMALE"),

        NON_BINARY("NON_BINARY");

        private String value;

        GenderEnum(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        @Override
        public String toString() {
            return String.valueOf(value);
        }
    }

    private ScoringDataDTO.GenderEnum gender;

    private LocalDate birthdate;

    private String passportSeries;

    private String passportNumber;

    private LocalDate passportIssueDate;

    private LocalDate passportIssueBranch;

    public enum MaritalStatusEnum {
        MARRIED("MARRIED"),

        DIVORCED("DIVORCED"),

        SINGLE("SINGLE"),

        WIDOW_WIDOWER("WIDOW_WIDOWER");

        private String value;

        MaritalStatusEnum(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        @Override
        public String toString() {
            return String.valueOf(value);
        }
    }

    private ScoringDataDTO.MaritalStatusEnum maritalStatus;

    private Integer dependentAmount;

    private EmploymentDTO employment;

    private String accountNumber;

    private Boolean isInsuranceEnabled;

    private Boolean isSalaryClient;
}
