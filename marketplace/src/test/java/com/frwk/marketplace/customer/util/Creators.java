package com.frwk.marketplace.customer.util;

import java.time.LocalDate;

import com.frwk.marketplace.customer.dto.CustomerDTO;

public class Creators {
    
    public static CustomerDTO createValidClient() {
        return CustomerDTO.builder().nome("Alex Vago").cpf("11600965696").dataNascimento(LocalDate.now().of(1994, 04, 15))
                .email("email@gmail.com").build();
    }

    public static CustomerDTO createInvalidClientCPF() {
        LocalDate.now();
        return CustomerDTO.builder().nome("Alex Vago").cpf("")
                .dataNascimento(LocalDate.of(1994, 04, 15))
                .email("email@gmail.com").build();
    }
}
