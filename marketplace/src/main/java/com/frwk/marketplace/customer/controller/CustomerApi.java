package com.frwk.marketplace.customer.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.frwk.marketplace.core.exceptions.InvalidClientException;
import com.frwk.marketplace.customer.dto.CustomerDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "Customer API")
public interface CustomerApi {
        
        @ApiOperation(value = "Operação de Cliente")
        @ApiResponses(value = {
                        @ApiResponse(code = 201, message = "Retorna o cliente criado."),
                        @ApiResponse(code = 412, message = "Dados obrigatórios faltando ou incorretos."),
                        @ApiResponse(code = 412, message = "Cliente não informado")
        })
        ResponseEntity<CustomerDTO> createCustomer(@RequestBody @Validated CustomerDTO customerDTO) throws InvalidClientException;

        @ApiOperation(value = "Operação para Atualizar um  Cliente")
        @ApiResponses(value = {
                        @ApiResponse(code = 202, message = "Retorna o cliente atualizado."),
                        @ApiResponse(code = 412, message = "Dados obrigatórios faltando ou incorretos."),
                        @ApiResponse(code = 412, message = "Cliente não informado", response = InvalidClientException.class)
        })
        ResponseEntity<CustomerDTO> updateCustomer(@RequestBody @Validated CustomerDTO customerDTO)
                        throws InvalidClientException;
        
        @ApiOperation(value = "Operação Buscar um  Cliente pelo cpf ")
        @ApiResponses(value = {
                        @ApiResponse(code = 200, message = "Retorna o cliente."),
                        @ApiResponse(code = 412, message = "Dados obrigatórios faltando ou incorretos."),
                        @ApiResponse(code = 412, message = "Cliente não informado", response = InvalidClientException.class)
        })
        ResponseEntity<CustomerDTO> findByCpf(@PathVariable String name) throws InvalidClientException;

        @ApiOperation(value = "Operação lista todos os Clientes")
        @ApiResponses(value = {
                        @ApiResponse(code = 200, message = "Retorna os clientes."),
                        @ApiResponse(code = 412, message = "Dados obrigatórios faltando ou incorretos."),
                        @ApiResponse(code = 412, message = "Cliente não informado", response = InvalidClientException.class)
        })
        ResponseEntity<List<CustomerDTO>> listCustomers();


}
