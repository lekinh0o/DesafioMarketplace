package com.frwk.marketplace.shoppingCart.repository.shoppingCart;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.frwk.marketplace.shoppingCart.model.shoppingCart.ShoppingCart;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, UUID> {


}
