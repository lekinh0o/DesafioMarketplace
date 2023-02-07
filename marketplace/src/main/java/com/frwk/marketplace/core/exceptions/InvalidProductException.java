package com.frwk.marketplace.core.exceptions;

public class InvalidProductException extends GeneralException {

    private static final long serialVersionUID = 1L;
   
    public InvalidProductException(String message) {
        super("InvalidProductException", message);
    }
}
