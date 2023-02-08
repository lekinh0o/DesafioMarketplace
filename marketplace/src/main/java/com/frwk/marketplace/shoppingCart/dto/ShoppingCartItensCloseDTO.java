package com.frwk.marketplace.shoppingCart.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.frwk.marketplace.product.dto.ProductDTO;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ShoppingCartItensCloseDTO {
    @JsonProperty("produto")
    private ProductDTO produto;

    @JsonProperty("quantidade")
    private Integer quantidade;
}
