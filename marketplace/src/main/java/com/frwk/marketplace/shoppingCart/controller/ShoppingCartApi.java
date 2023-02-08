package com.frwk.marketplace.shoppingCart.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.frwk.marketplace.core.exceptions.GeneralException;
import com.frwk.marketplace.core.exceptions.InvalidCartException;
import com.frwk.marketplace.core.exceptions.InvalidClientException;
import com.frwk.marketplace.core.exceptions.InvalidProductException;
import com.frwk.marketplace.customer.dto.CustomerDTO;
import com.frwk.marketplace.product.dto.ProductCreateDTO;
import com.frwk.marketplace.shoppingCart.dto.ShoppingCartCloseDTO;
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
                        
        @ApiOperation(value = "Operação para adicionar um produto a um carrinho")
        @ApiResponses(value = {
                        @ApiResponse(code = 200, message = "Produto adicionado com sucesso"),
                        @ApiResponse(code = 412, message = "Dados obrigatorios faltando ou incorretos.", response = GeneralException.class),
                        @ApiResponse(code = 412, message = "Carrinho informado é inválido", response = InvalidCartException.class),
                        @ApiResponse(code = 412, message = "Carrinho informado ja se encontra fechado", response = InvalidCartException.class),
                        @ApiResponse(code = 412, message = "A quantidade informada é menor que 1", response = InvalidProductException.class),
                        @ApiResponse(code = 412, message = "Produto informado é inválido", response = InvalidProductException.class)
        })
        ResponseEntity<Void> includeproductInCart(@RequestBody @Validated ProductCreateDTO body)
                        throws InvalidCartException;

        
        @ApiOperation(value = "Operação para fechar um carrinho")
        @ApiResponses(value = {
                        @ApiResponse(code = 200, message = "Retorna dados do carinho fechado"),
                        @ApiResponse(code = 412, message = "Carrinho informado é inválido", response = InvalidCartException.class),
                        @ApiResponse(code = 412, message = "Carrinho informado ja se encontra fechado", response = InvalidCartException.class),
                        @ApiResponse(code = 412, message = "O carrinho não possui produtos.", response = InvalidCartException.class),
        })
        ResponseEntity<ShoppingCartCloseDTO> closeShoppingCart(@PathVariable String idCarrinho) 
                        throws InvalidCartException;



}
