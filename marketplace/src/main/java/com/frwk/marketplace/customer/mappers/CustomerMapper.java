package com.frwk.marketplace.customer.mappers;

import org.springframework.stereotype.Component;

import com.frwk.marketplace.core.shared.ParserMapDTO;
import com.frwk.marketplace.customer.dto.CustomerDTO;
import com.frwk.marketplace.customer.model.Customer;

@Component
public class CustomerMapper implements ParserMapDTO<CustomerDTO, Customer> {
    @Override
    public CustomerDTO mapEntityFromDTO(Customer customer) {
        return CustomerDTO.builder().cpf(customer.getIdentificationCode())
                .nome(customer.getName())
                .dataNascimento(customer.getBirthDate())
                .email(customer.getEmail()).build();
    }

    @Override
    public Customer mapDTOFromEntity(CustomerDTO dto) {
        return Customer.builder()
                .identificationCode(dto.getCpf())
                .name(dto.getNome())
                .birthDate(dto.getDataNascimento())
                .email(dto.getEmail())
                .build();
    }
}
