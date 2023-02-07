package com.frwk.marketplace.core.exceptions;

import lombok.Getter;
import lombok.Setter;


@Getter@Setter
public class InvalidClientException extends GeneralException {
    public InvalidClientException(String message) {
        super("InvalidClientException",message);
    }

}