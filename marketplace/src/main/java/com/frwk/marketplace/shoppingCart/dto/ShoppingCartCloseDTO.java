package com.frwk.marketplace.shoppingCart.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.frwk.marketplace.customer.dto.CustomerDTO;

import lombok.Builder;
import lombok.Data;

@Data@Builder
public class ShoppingCartCloseDTO {

    @JsonProperty("idCarrinho")
    private String idShoppingCart;

    @JsonProperty("cliente")
    private CustomerDTO customer;
    
    @JsonProperty("itens")
    private List<ShoppingCartItensCloseDTO> itens;
}