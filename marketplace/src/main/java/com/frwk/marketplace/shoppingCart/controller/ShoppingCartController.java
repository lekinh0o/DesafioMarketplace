package com.frwk.marketplace.shoppingCart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.frwk.marketplace.core.exceptions.InvalidCartException;
import com.frwk.marketplace.core.exceptions.InvalidClientException;
import com.frwk.marketplace.customer.dto.CustomerDTO;
import com.frwk.marketplace.product.dto.ProductCreateDTO;
import com.frwk.marketplace.shoppingCart.dto.ShoppingCartCloseDTO;
import com.frwk.marketplace.shoppingCart.dto.ShoppingCartCreatedDTO;
import com.frwk.marketplace.shoppingCart.model.enums.StatusCart;
import com.frwk.marketplace.shoppingCart.service.ShoppingCartService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1/marketplace/carrinho")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ShoppingCartController implements ShoppingCartApi {
    
    private ShoppingCartService shoppingCartService;

   
    @Override
    @PostMapping()
    public ResponseEntity<ShoppingCartCreatedDTO> createShoppingCart(
            @RequestBody @Validated CustomerDTO customerDTO) throws InvalidClientException {
                 ShoppingCartCreatedDTO shoppingcart  = this.shoppingCartService.createShoppingCart(customerDTO);
                 boolean isNew = StatusCart.OPEN_NEW.name().equals(shoppingcart.getStatus());
                 return new ResponseEntity<>(shoppingcart, isNew ? HttpStatus.CREATED : HttpStatus.OK);
    }

    @Override
    @PostMapping("/produto")
    public ResponseEntity<Void> includeproductInCart(@RequestBody @Validated ProductCreateDTO productCreateDTO)
            throws InvalidCartException {
        this.shoppingCartService.includeproductInCart(productCreateDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @Override
    @PostMapping("/fechar/{idCarrinho}")
    public ResponseEntity<ShoppingCartCloseDTO> closeShoppingCart(@PathVariable String idCarrinho) throws InvalidCartException {
        return new ResponseEntity<>(this.shoppingCartService.closeShoppingCart(idCarrinho),HttpStatus.ACCEPTED);
    }

}
