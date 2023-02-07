package com.frwk.marketplace.customer.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.frwk.marketplace.customer.dto.CustomerDTO;
import com.frwk.marketplace.customer.mappers.CustomerMapper;
import com.frwk.marketplace.customer.model.Customer;
import com.frwk.marketplace.customer.repository.CustomerRepository;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository repository;

    @Autowired
    private CustomerMapper mapper;

    public CustomerDTO createCustomer(CustomerDTO customerDTO) throws Exception {
        this.verifyIfIsAlreadyRegistered(customerDTO.getCpf());
        Customer customer = this.mapper.mapDTOFromEntity(customerDTO);
        Customer savedCustomer = this.repository.save(customer);
        return this.mapper.mapEntityFromDTO(savedCustomer);
    }

    private void verifyIfIsAlreadyRegistered(String code) throws Exception {
        Customer optSaved = this.repository.findByIdentificationCode(code);
        if (optSaved != null) {
            throw new Exception((String.format("Cliente com CPF %s já cadastrado no sistema.", code)));
        }
    }

}
