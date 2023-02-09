package com.frwk.marketplace.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import com.frwk.marketplace.customer.dto.CustomerDTO;
import com.frwk.marketplace.customer.model.Customer;
import com.frwk.marketplace.product.dto.ProductCreateDTO;
import com.frwk.marketplace.product.dto.ProductDTO;
import com.frwk.marketplace.product.model.Product;
import com.frwk.marketplace.shoppingCart.dto.ShoppingCartCloseDTO;
import com.frwk.marketplace.shoppingCart.dto.ShoppingCartCreatedDTO;
import com.frwk.marketplace.shoppingCart.dto.ShoppingCartItensCloseDTO;
import com.frwk.marketplace.shoppingCart.model.ShoppingCart;
import com.frwk.marketplace.shoppingCart.model.ShoppingCartItens;
import com.frwk.marketplace.shoppingCart.model.enums.StatusCart;

public class Creators {
    public final static String NOME = "Alex Vago";
    public final static String CPF = "11600965696";
    public final static String EMAIL = "email@gmail.com";
    public final static LocalDate DATA = LocalDate.of(1994, 04, 15);

    public final static String NOME_PRODUTO = "BALA";

    public static CustomerDTO createValidCustomerDTO() {
        return CustomerDTO.builder().nome(NOME).cpf(CPF).dataNascimento(getLocaldateToString(DATA))
                .email(EMAIL).build();
    }

    public static CustomerDTO createCustomerDTOInvalidCPF() {

        return CustomerDTO.builder().nome(NOME).cpf(null)
                .dataNascimento(
                        getLocaldateToString(DATA))
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

    public static ShoppingCart shoppingCartAndProductCart(StatusCart status) {
        UUID id = UUID.randomUUID();
        ShoppingCart cartAux = ShoppingCart.builder().id(id).status(status).build();
        return ShoppingCart.builder().id(id).status(status).customer(createCustomerNewCart(cartAux)).itens(
                shoppingCartItensCartList(cartAux))
                .build();
    }

    public static List<ShoppingCartItens> shoppingCartItensCartList(ShoppingCart cartAux) {

        ShoppingCartItens shoppingCartItens = ShoppingCartItens.builder().id(1L).product(
                createProduto()).quantity(5).shoppingCart(cartAux)
                .build();
        ArrayList<ShoppingCartItens> itens = new ArrayList();
        itens.add(shoppingCartItens);
        return itens;
    }

    
    public static ProductCreateDTO productCreateDTO (ShoppingCart cart) {
        return ProductCreateDTO.builder().idProduto("988766").idCarrinho(cart.getId().toString()).quantidade(5).build();
    }
    

    public static Product createProduto() {
        return Product.builder().id(988766L).price(10.0).build();
    }
    
    public static Product createProdutoNewAdd() {
        return Product.builder().id(111111L).price(10.0).build();
    }

    public static ShoppingCartCloseDTO shoppingCartCloseDTO(ShoppingCart shoppingCart) {
        return ShoppingCartCloseDTO.builder().customer(createValidCustomerDTO()).idShoppingCart(shoppingCart.getId().toString()).itens(
                shoppingCartIntensCloseDTO()).build();
    }

    public static List<ShoppingCartItensCloseDTO> shoppingCartIntensCloseDTO() {
        return Collections.singletonList( ShoppingCartItensCloseDTO.builder().produto( createProductDTO()).quantidade(10).build());

    }

    private static String getLocaldateToString(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String dateString = date.format(formatter);

        return dateString;
    }
}