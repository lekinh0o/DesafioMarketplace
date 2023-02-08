package com.frwk.marketplace.product.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.frwk.marketplace.product.dto.ProductDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "Product API")
public interface ProductApi {
    @ApiOperation(value = "Operação lista todos os produtos")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Retorna os produtos."),
            @ApiResponse(code = 412, message = "Dados obrigatórios faltando ou incorretos.")
    })
    ResponseEntity<List<ProductDTO>> findAllProduct();
}
