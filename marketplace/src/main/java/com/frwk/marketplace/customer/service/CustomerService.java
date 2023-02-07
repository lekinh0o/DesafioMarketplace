package com.frwk.marketplace.customer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.frwk.marketplace.core.exceptions.InvalidClientException;
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

    public CustomerDTO createCustomer(CustomerDTO customerDTO) throws InvalidClientException {
        this.verifyIfIsAlreadyRegistered(customerDTO.getCpf());
        Customer customer = this.mapper.mapDTOFromEntity(customerDTO);
        Customer savedCustomer = this.repository.save(customer);
        return this.mapper.mapEntityFromDTO(savedCustomer);
    }

    private void verifyIfIsAlreadyRegistered(String code) throws InvalidClientException {
        Customer optSaved = repository.findByIdentificationCode(code);
        if (optSaved != null) {
            throw new InvalidClientException(String.format("Cliente com CPF %s já cadastrado no sistema.", code));
        }
    }

}
