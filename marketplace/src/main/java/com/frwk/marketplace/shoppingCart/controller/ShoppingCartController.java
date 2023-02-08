package com.frwk.marketplace.shoppingCart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.frwk.marketplace.core.exceptions.InvalidClientException;
import com.frwk.marketplace.customer.dto.CustomerDTO;
import com.frwk.marketplace.shoppingCart.dto.ShoppingCartCreatedDTO;
import com.frwk.marketplace.shoppingCart.model.enums.StatusCart;
import com.frwk.marketplace.shoppingCart.service.ShoppingCartService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1/marketplace/carrinho")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ShoppingCartController implements ShoppingCartApi {
    
    private ShoppingCartService shoppingCartService;

    @PostMapping()
    @Override
    public ResponseEntity<ShoppingCartCreatedDTO> createShoppingCart(
            @RequestBody @Validated CustomerDTO customerDTO) throws InvalidClientException {
                 ShoppingCartCreatedDTO shoppingcart  = this.shoppingCartService.createShoppingCart(customerDTO);
                 boolean isNew = StatusCart.OPEN_NEW.name().equals(shoppingcart.getStatus());
                 return new ResponseEntity<>(shoppingcart, isNew ? HttpStatus.CREATED : HttpStatus.OK);
    }


}
