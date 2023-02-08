package com.frwk.marketplace.shoppingCart.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.frwk.marketplace.shoppingCart.model.ShoppingCartItens;

public interface ShoppingCartItensRepository extends JpaRepository<ShoppingCartItens, Long> {

}
