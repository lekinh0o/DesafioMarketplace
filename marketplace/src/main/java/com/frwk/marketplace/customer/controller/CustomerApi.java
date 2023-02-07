package com.frwk.marketplace.customer.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;

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
        ResponseEntity<CustomerDTO> createCustomer(@RequestBody @Validated CustomerDTO customerDTO) throws Exception;


}
