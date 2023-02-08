package com.frwk.marketplace.core.shared;

public interface ParserMapDTO<D,E> {
    
    public D mapEntityFromDTO(final E entity);

    public E mapDTOFromEntity(final D dto);
}
