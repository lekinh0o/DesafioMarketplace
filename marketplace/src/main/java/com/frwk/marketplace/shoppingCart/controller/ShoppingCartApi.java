package com.frwk.marketplace.shoppingCart.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RequestBody;

import com.frwk.marketplace.core.exceptions.InvalidClientException;
import com.frwk.marketplace.customer.dto.CustomerDTO;
import com.frwk.marketplace.shoppingCart.dto.ShoppingCartCreatedDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "ShoppingCart API")
public interface ShoppingCartApi {
        @ApiOperation(value = "Operação de criação de um carrinho")
        @ApiResponses(value = {
                        @ApiResponse(code = 201, message = "Retorna o ID do carrinho criado."),
                        @ApiResponse(code = 200, message = "Retorna o ID do carrinho que se encontra aberto."),
                        @ApiResponse(code = 412, message = "Dados obrigatorios faltando ou incorretos."),
                        @ApiResponse(code = 412, message = "Cliente não informado", response = InvalidClientException.class)
        })
        ResponseEntity<ShoppingCartCreatedDTO> createShoppingCart(
                        @RequestBody @Validated CustomerDTO customerDTO) throws MethodArgumentNotValidException,
                        InvalidClientException;


}
