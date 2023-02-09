package com.frwk.marketplace.customer.service;

import java.util.List;
import java.util.stream.Collectors;

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
        return this.mapper.mapEntityFromDTO(this.repository.save(customer));
    }

    public void verifyIfIsAlreadyRegistered(String code) throws InvalidClientException {
        Customer optSaved = repository.findByIdentificationCode(code);
        if (optSaved != null) {
            throw new InvalidClientException(String.format("Cliente com CPF %s já cadastrado no sistema.", code));
        }
    }

    public CustomerDTO save(CustomerDTO customerDTO) throws InvalidClientException {
        Customer customer = this.repository.findByIdentificationCode(customerDTO.getCpf());
        if (customer != null) {
            this.repository.save(this.mapper.mapDTOFromEntity(customerDTO));
            return this.mapper.mapEntityFromDTO(this.repository.save(customer));
        }
        throw new InvalidClientException("Cliente não esta cadastrado na sistema");
    }

    public CustomerDTO findByIdentificationCode(String code) throws InvalidClientException {
        Customer customer = this.repository.findByIdentificationCode(code);
        if (customer == null) {
            throw new InvalidClientException("Cliente não esta cadastrado na sistema");
        }
        return this.mapper.mapEntityFromDTO(customer);
    }

    public List<CustomerDTO> listCustomers() {
        return this.repository.findAll()
                .stream().map(customer -> this.mapper.mapEntityFromDTO(customer))
                .collect(Collectors.toList());
    }
    
    public Customer findByIdentificationCodeEntity(String code) throws InvalidClientException {
        return this.repository.findByIdentificationCode(code);
    }

    public Customer saveEntity(Customer customer) {
        return this.repository.save(customer);
    }
}
