package com.igorpetrovcm.neoloan.calculator.usecase.exception;

public class OfferSettingsValueException extends RuntimeException{
    public OfferSettingsValueException(String message){
        super(message);
    }

    public OfferSettingsValueException(String message, Throwable cause){
        super(message, cause);
    }
}
