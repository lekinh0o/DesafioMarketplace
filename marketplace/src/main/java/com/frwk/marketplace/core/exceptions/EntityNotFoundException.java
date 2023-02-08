package com.frwk.marketplace.core.exceptions;

public class EntityNotFoundException extends GeneralException {
  
    private static final long serialVersionUID = 1L;

    public EntityNotFoundException(String message) {
        super("EntityNotFoundException", message);
    }
}
