package com.frwk.marketplace.shoppingCart.repository.shoppingCart;

import org.springframework.data.jpa.repository.JpaRepository;

import com.frwk.marketplace.shoppingCart.model.shoppingCart.ShoppingCartItens;

public interface ShoppingCartItensRepository extends JpaRepository<ShoppingCartItens, Long> {

}
