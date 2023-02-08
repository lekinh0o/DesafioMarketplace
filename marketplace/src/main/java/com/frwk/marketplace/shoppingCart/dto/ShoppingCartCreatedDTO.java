package com.frwk.marketplace.shoppingCart.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;

@Data@Builder
public class ShoppingCartCreatedDTO {
	
    @JsonProperty("idCarrinho")
    private String idCarrinho;

    @JsonIgnore
    private transient String  status;
	    
	
}

