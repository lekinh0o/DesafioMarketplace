package com.frwk.marketplace.core.shared;

import com.frwk.marketplace.core.exceptions.InvalidClientException;

public interface ParserMapDTO<D,E> {
    
    public D mapEntityFromDTO(final E entity);

    public E mapDTOFromEntity(final D dto) throws InvalidClientException;
}
