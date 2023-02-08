package com.frwk.marketplace.customer.util;

import java.time.LocalDate;

import com.frwk.marketplace.customer.dto.CustomerDTO;
import com.frwk.marketplace.customer.model.Customer;

public class Creators {
    public final static String NOME = "Alex Vago";
    public final static String CPF = "11600965696";
    public final static String EMAIL = "email@gmail.com";
    public final static LocalDate DATA = LocalDate.of(1994, 04, 15);
    


    public static CustomerDTO createValidCustomerDTO() {
        return CustomerDTO.builder().nome(NOME).cpf(CPF).dataNascimento(DATA)
                .email(EMAIL).build();
    }

    public static CustomerDTO createCustomerDTOInvalidCPF() {
        LocalDate.now();
        return CustomerDTO.builder().nome(NOME).cpf("")
                .dataNascimento(DATA)
                .email(EMAIL).build();
    }

    public static Customer createCustomer() {
        LocalDate.now();
        return Customer.builder().id(1L).name(NOME).identificationCode(CPF)
                .birthDate(DATA)
                .email(EMAIL).build();
    }
}
