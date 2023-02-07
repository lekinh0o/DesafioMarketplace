package com.frwk.marketplace.shoppingCart.repository.product;

import org.springframework.data.jpa.repository.JpaRepository;

import com.frwk.marketplace.shoppingCart.model.product.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
