package com.igorpetrovcm.neoloan.calculator.usecase.valid;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface LegalAgeConstraint {
    String message() default "Doesn't pass legal age constraint";
}
