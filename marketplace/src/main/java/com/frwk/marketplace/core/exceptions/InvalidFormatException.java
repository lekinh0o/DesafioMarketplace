package com.frwk.marketplace.core.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class InvalidFormatException extends GeneralException {

    private final String field;

    public InvalidFormatException(String field, String message) {
        super("ModelValidationException", message);
        this.field = field;
    }
}

