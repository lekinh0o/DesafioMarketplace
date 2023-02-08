package com.frwk.marketplace.shoppingCart.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.frwk.marketplace.shoppingCart.model.ShoppingCart;
import com.frwk.marketplace.shoppingCart.model.ShoppingCartItens;
import com.frwk.marketplace.shoppingCart.repository.ShoppingCartItensRepository;

import lombok.AllArgsConstructor;
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ShoppingCartItensService {

    private ShoppingCartItensRepository repository;

    public Optional<ShoppingCartItens> findById(Long idShoppingCart) {

        return this.repository.findByid(idShoppingCart);
    }

    public List<ShoppingCartItens> findAllItensByShoppingCart(ShoppingCart shoppingCart) {
        return this.repository.findAllItensByShoppingCart(shoppingCart);
    }

    public ShoppingCartItens saveCartItem(ShoppingCartItens shoppingCartItens) {
        return this.repository.save(shoppingCartItens);
    }

}
