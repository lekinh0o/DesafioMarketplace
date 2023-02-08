package com.frwk.marketplace.util;

import java.time.LocalDate;

import com.frwk.marketplace.customer.dto.CustomerDTO;
import com.frwk.marketplace.customer.model.Customer;
import com.frwk.marketplace.product.dto.ProductDTO;
import com.frwk.marketplace.product.model.Product;

public class Creators {
    public final static String NOME = "Alex Vago";
    public final static String CPF = "11600965696";
    public final static String EMAIL = "email@gmail.com";
    public final static LocalDate DATA = LocalDate.of(1994, 04, 15);

    public final static String NOME_PRODUTO = "BALA";

    public static CustomerDTO createValidCustomerDTO() {
        return CustomerDTO.builder().nome(NOME).cpf(CPF).dataNascimento(DATA)
                .email(EMAIL).build();
    }

    public static CustomerDTO createCustomerDTOInvalidCPF() {

        return CustomerDTO.builder().nome(NOME).cpf("")
                .dataNascimento(DATA)
                .email(EMAIL).build();
    }

    public static Customer createCustomer() {
        return Customer.builder().id(1L).name(NOME).identificationCode(CPF)
                .birthDate(DATA)
                .email(EMAIL).build();
    }

    public static Product createProduct() {
        return Product.builder().id(1L).name(NOME_PRODUTO).price(10.0).build();
    }

    public static ProductDTO createProductDTO() {
        return ProductDTO.builder().id(1L).name(NOME_PRODUTO).price(10.0).build();
    }

}