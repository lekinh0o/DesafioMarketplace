package com.frwk.marketplace.shoppingCart.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.frwk.marketplace.shoppingCart.model.ShoppingCart;
import com.frwk.marketplace.shoppingCart.model.ShoppingCartItens;

public interface ShoppingCartItensRepository extends JpaRepository<ShoppingCartItens, Long> {

    Optional<ShoppingCartItens> findByid(Long idShoppingCartItens);
    List<ShoppingCartItens> findAllItensByShoppingCart(ShoppingCart shoppingCart);

}
