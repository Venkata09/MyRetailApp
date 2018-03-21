package com.myRetail.app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author vdokku
 */

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Price information is not found in DB. ")
public class PriceNotFoundException extends Exception {

    private int errorCode;
    private String errorMessage;


    public PriceNotFoundException(){}

    public PriceNotFoundException(int errorCode, String errorMessage) {
        super(errorMessage);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
