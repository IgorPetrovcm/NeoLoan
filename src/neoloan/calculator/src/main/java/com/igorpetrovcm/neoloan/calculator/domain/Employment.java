package com.igorpetrovcm.neoloan.calculator.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Employment {
    public enum EmploymentStatusEnum {
        UNEMPLOYED("UNEMPLOYED"),

        SELF_EMPLOYED("SELF_EMPLOYED"),

        EMPLOYED("EMPLOYED"),

        BUSINESS_OWNER("BUSINESS_OWNER");

        private String value;

        EmploymentStatusEnum(String value) {
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

    private EmploymentStatusEnum employmentStatus;

    private String employerINN;

    private BigDecimal salary;

    public enum PositionEnum {
        WORKER("WORKER"),

        MID_MANAGER("MID_MANAGER"),

        TOP_MANAGER("TOP_MANAGER"),

        OWNER("OWNER");

        private String value;

        PositionEnum(String value) {
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

    private PositionEnum position;

    private Integer workExperienceTotal;

    private Integer workExperienceCurrent;
}
