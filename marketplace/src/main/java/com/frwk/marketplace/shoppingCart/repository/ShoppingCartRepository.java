package com.frwk.marketplace.shoppingCart.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.frwk.marketplace.shoppingCart.model.ShoppingCart;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, UUID> {


}
