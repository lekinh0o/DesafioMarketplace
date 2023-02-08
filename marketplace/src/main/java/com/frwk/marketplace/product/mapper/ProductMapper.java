package com.frwk.marketplace.product.mapper;

import org.springframework.stereotype.Component;

import com.frwk.marketplace.core.shared.ParserMapDTO;
import com.frwk.marketplace.product.dto.ProductDTO;
import com.frwk.marketplace.product.model.Product;

@Component
public class ProductMapper implements ParserMapDTO<ProductDTO, Product> {

    @Override
    public ProductDTO mapEntityFromDTO(Product entity) {
        return ProductDTO.builder().id(entity.getId()).name(entity.getName()).price(entity.getPrice()).build();
    }

    @Override
    public Product mapDTOFromEntity(ProductDTO dto) {
        return Product.builder().id(dto.getId()).name(dto.getName()).price(dto.getPrice()).build();
    }

}
