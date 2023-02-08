package com.frwk.marketplace.util;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

import com.frwk.marketplace.customer.dto.CustomerDTO;
import com.frwk.marketplace.customer.model.Customer;
import com.frwk.marketplace.product.dto.ProductDTO;
import com.frwk.marketplace.product.model.Product;
import com.frwk.marketplace.shoppingCart.dto.ShoppingCartCreatedDTO;
import com.frwk.marketplace.shoppingCart.model.ShoppingCart;
import com.frwk.marketplace.shoppingCart.model.enums.StatusCart;

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

        return CustomerDTO.builder().nome(NOME).cpf(null)
                .dataNascimento(DATA)
                .email(EMAIL).build();
    }

    public static Customer createCustomer() {
        return Customer.builder().id(1L).name(NOME).identificationCode(CPF)
                .birthDate(DATA)
                .email(EMAIL).build();
    }

    public static Customer createCustomerNewCart(ShoppingCart shoppingCart) {
        Customer customer = Customer.builder().id(1L).name(NOME).identificationCode(CPF)
                .birthDate(DATA)
                .email(EMAIL).build();
        ArrayList<ShoppingCart> carts = new ArrayList();
        carts.add(shoppingCart);
        customer.setShoppingCart(carts);
        return customer;
    }

    public static Product createProduct() {
        return Product.builder().id(1L).name(NOME_PRODUTO).price(10.0).build();
    }

    public static ProductDTO createProductDTO() {
        return ProductDTO.builder().id(1L).name(NOME_PRODUTO).price(10.0).build();
    }

    public static ShoppingCartCreatedDTO shoppingCartDTOCreate(StatusCart status) {
        return ShoppingCartCreatedDTO.builder().idCarrinho(UUID.randomUUID().toString()).status(status.name()).build();
    }

    public static ShoppingCartCreatedDTO shoppingCartDTOCreateNewCart(StatusCart status, UUID id) {
        return ShoppingCartCreatedDTO.builder().idCarrinho(id.toString()).status(status.name()).build();
    }

    public static ShoppingCart shoppingCartNewCart(StatusCart status) {
        UUID id = UUID.randomUUID();
        ShoppingCart cartAux = ShoppingCart.builder().id(id).status(status).build();
        return ShoppingCart.builder().id(id).status(status).customer(createCustomerNewCart(cartAux)).build();
    }
}