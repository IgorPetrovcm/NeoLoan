package com.igorpetrovcm.neoloan.calculator.adapter.offercalculator;

import com.igorpetrovcm.neoloan.calculator.domain.Employment;
import com.igorpetrovcm.neoloan.calculator.domain.Offer;
import com.igorpetrovcm.neoloan.calculator.domain.Statement;
import com.igorpetrovcm.neoloan.calculator.usecase.exception.OfferSettingsValueException;
import com.igorpetrovcm.neoloan.calculator.usecase.port.MonthlyPaymentCalculator;
import com.igorpetrovcm.neoloan.calculator.usecase.port.OfferSettings;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@RequiredArgsConstructor
public class OfferValuesSettings implements OfferSettings {

    private final MonthlyPaymentCalculator monthlyPaymentCalculator;

    @Value("${offers-calculator.base-rate}")
    private BigDecimal baseRate;

    @Value("${offers-calculator.base-insurance-rate}")
    private volatile BigDecimal insuranceBaseRate;

    @Override
    public OfferSettings validateValues(Statement statement){
        Objects.requireNonNull(statement.getBirthdate());
        Objects.requireNonNull(statement.getEmployment());

        int yearsOld = Period.between(statement.getBirthdate(), LocalDate.now())
                .getYears();

        if (yearsOld < 20 || yearsOld > 65){
            throw new OfferSettingsValueException("no wrong age");
        }

        Employment employmentAlias = statement.getEmployment();

        if (employmentAlias.getSalary()
                .multiply(BigDecimal.valueOf(24))
                .compareTo(statement.getAmount()) < 0)
        {
            throw new OfferSettingsValueException("small salary");
        }

        if (employmentAlias.getWorkExperienceTotal() < 18
                || employmentAlias.getWorkExperienceCurrent() < 3)
        {
            throw new OfferSettingsValueException("small experience");
        }

        return this;
    }

    @Override
    public OfferSettings setId(Offer offer) {
        offer.setStatementId(UUID.randomUUID());

        return this;
    }

    @Override
    public OfferSettings setTerm(Statement statement, Offer offer) {
        offer.setTerm(statement.getTerm());

        return this;
    }

    @Override
    public OfferSettings setRequestedAmount(Statement statement, Offer offer) {
        offer.setRequestedAmount(statement.getAmount());

        return this;
    }

    @Override
    public OfferSettings setAmount(Statement statement, Offer offer) {
        if (statement.getIsInsuranceEnabled() && statement.getIsSalaryClient()){
            offer.setTotalAmount(statement.getAmount());
            offer.setIsInsuranceEnabled(true);
            offer.setIsSalaryClient(true);
            return this;
        }

        if (!statement.getIsInsuranceEnabled()){
            offer.setTotalAmount(statement.getAmount());
            offer.setIsInsuranceEnabled(false);
        }

        if (statement.getIsInsuranceEnabled()){
            offer.setTotalAmount(statement.getAmount()
                    .add(calculateInsurance(statement.getAmount()))
            );

            offer.setIsInsuranceEnabled(true);
            offer.setIsSalaryClient(false);
            return this;
        }

        if (statement.getIsSalaryClient()){
            offer.setIsSalaryClient(true);
        }
        else {
            offer.setIsSalaryClient(false);
        }

        return this;
    }

    @Override
    public OfferSettings setRate(Statement statement, Offer offer) throws OfferSettingsValueException {
        int rateAdditional = 0;

        if (offer.getIsInsuranceEnabled()){
            rateAdditional -= 3;
        }

        if (offer.getIsSalaryClient()){
            rateAdditional -= 1;
        }

        if (offer.getIsInsuranceEnabled() && offer.getIsSalaryClient()){
            rateAdditional = -1;
        }

        if (statement.getEmployment() != null){
            Employment employmentAlias = statement.getEmployment();
            Objects.requireNonNull(employmentAlias.getEmploymentStatus());
            Objects.requireNonNull(employmentAlias.getPosition());

            switch (employmentAlias.getEmploymentStatus()){
                case UNEMPLOYED:
                    throw new OfferSettingsValueException("unemployed");
                case SELF_EMPLOYED:
                    rateAdditional += 2;
                    break;
                case BUSINESS_OWNER:
                    rateAdditional += 1;
                    break;
                default:
                    break;
            }

            switch (employmentAlias.getPosition()){
                case MID_MANAGER:
                    rateAdditional -= 2;
                    break;
                case TOP_MANAGER:
                    rateAdditional -= 3;
                    break;
                default:
                    break;
            }
        }

        if (statement.getGender() != null){
            Objects.requireNonNull(statement.getBirthdate());
            int yearsOld = Period.between(statement.getBirthdate(), LocalDate.now()).getYears();

            switch (statement.getGender()){
                case MALE:
                    if (yearsOld >= 30 && yearsOld <= 55){
                        rateAdditional -= 3;
                    }
                    break;
                case FEMALE:
                    if (yearsOld >= 32 && yearsOld <= 60){
                        rateAdditional -= 3;
                    }
                    break;
                case NON_BINARY:
                    rateAdditional += 7;
                    break;
            }
        }

        if (statement.getMaritalStatus() != null){
            switch (statement.getMaritalStatus()){
                case MARRIED:
                    rateAdditional -= 3;
                    break;
                case DIVORCED:
                    rateAdditional += 1;
                    break;
                default:
                    break;
            }
        }

        offer.setRate(baseRate.add(BigDecimal.valueOf(rateAdditional)));

        return this;
    }

    @Override
    public OfferSettings setMonthlyPayment(Offer offer) {
        offer.setMonthlyPayment(monthlyPaymentCalculator.calculating(
                offer.getTerm(),
                offer.getRate(),
                offer.getTotalAmount()));

        return this;
    }

    @Override
    public OfferSettings setPsk(Statement statement, Offer offer) {
        return this;
    }

    private BigDecimal calculateInsurance(BigDecimal amount){
        BigDecimal insuranceProgressiveRate = insuranceBaseRate;
        BigDecimal totalInsurance = BigDecimal.valueOf(0);
        BigDecimal localAmount = amount;
        List<BigDecimal> amountProgressive = new ArrayList<>();

        int rangeCount = 0;

        for ( ; localAmount.compareTo(BigDecimal.valueOf(0)) > 0; rangeCount++){
            localAmount = localAmount.subtract(BigDecimal.valueOf(500000));
        }

        for (int i = 1; i < rangeCount; i++){
            amountProgressive.add(BigDecimal.valueOf(500000));
        }

        amountProgressive.add(BigDecimal.valueOf(500000).add(localAmount));

        for (int i = 0; i < rangeCount; i++){
            totalInsurance = totalInsurance.add(
                    amountProgressive.get(i)
                            .multiply(insuranceProgressiveRate)
                            .divide(BigDecimal.valueOf(100), RoundingMode.HALF_UP)
            );

            insuranceProgressiveRate = insuranceProgressiveRate.add(BigDecimal.valueOf(0.2));
        }

        return totalInsurance;
    }
}
