package com.frwk.marketplace.product.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.frwk.marketplace.product.dto.ProductDTO;
import com.frwk.marketplace.product.service.ProductService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1/marketplace/produto")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ProductController implements ProductApi {

    private ProductService productService;

    @Override
    @GetMapping
    public ResponseEntity<List<ProductDTO>> findAllProduct() {
        return new ResponseEntity<>(this.productService.findAll(), HttpStatus.OK);
    }

}
