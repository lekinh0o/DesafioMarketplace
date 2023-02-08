package com.frwk.marketplace.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.frwk.marketplace.product.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
