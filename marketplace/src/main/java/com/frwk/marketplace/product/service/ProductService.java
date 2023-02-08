package com.frwk.marketplace.product.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.frwk.marketplace.product.dto.ProductDTO;
import com.frwk.marketplace.product.mapper.ProductMapper;
import com.frwk.marketplace.product.model.Product;
import com.frwk.marketplace.product.repository.ProductRepository;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    @Autowired
    ProductMapper mapper;

    public List<ProductDTO> findAll() {
        return this.repository.findAll().stream().map(product -> this.mapper.mapEntityFromDTO(product))
                .collect(Collectors.toList());

    }

    public Optional<Product> findById(Long idProduct) {
        return this.repository.findById(idProduct);
    }

}
