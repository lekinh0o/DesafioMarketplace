package com.frwk.marketplace.shoppingCart.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.frwk.marketplace.core.shared.ParserMapDTO;
import com.frwk.marketplace.product.mapper.ProductMapper;
import com.frwk.marketplace.shoppingCart.dto.ShoppingCartItensCloseDTO;
import com.frwk.marketplace.shoppingCart.model.ShoppingCartItens;

@Component
public class ShoppingCartItensMapper implements ParserMapDTO<ShoppingCartItensCloseDTO, ShoppingCartItens> {
   
    @Autowired
    private ProductMapper productMapper;

    @Override
    public ShoppingCartItensCloseDTO mapEntityFromDTO(ShoppingCartItens entity) {
        return ShoppingCartItensCloseDTO.builder().produto(this.productMapper.mapEntityFromDTO(entity.getProduct()))
                .quantidade(entity.getQuantity()).build();
    }

    @Override
    public ShoppingCartItens mapDTOFromEntity(ShoppingCartItensCloseDTO dto) {
        return ShoppingCartItens.builder().product(this.productMapper.mapDTOFromEntity(dto.getProduto()))
                .quantity(dto.getQuantidade()).build();
    }

}
