package com.frwk.marketplace.core.exceptions;

public class InvalidCartException extends GeneralException {

    private static final long serialVersionUID = 1L;

    public InvalidCartException(String message) {
        super("InvalidCartException", message);
    }

}
