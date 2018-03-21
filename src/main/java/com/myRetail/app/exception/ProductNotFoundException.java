package com.myRetail.app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author vdokku
 */

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Product data is not available")
public class ProductNotFoundException extends Exception {

    private int errorCode;
    private String errorMessage;


    public ProductNotFoundException(){}

    public ProductNotFoundException(int errorCode, String errorMessage) {
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
