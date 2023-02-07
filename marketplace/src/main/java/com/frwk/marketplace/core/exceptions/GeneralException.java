package com.frwk.marketplace.core.exceptions;

import lombok.Getter;

@Getter
public class GeneralException extends Exception {

    private static final long serialVersionUID = 1L;

    public final String type;

    public GeneralException(String type, String message) {
        super(message);
        this.type = type;
    }

}
