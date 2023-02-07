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
        Customer savedCustomer = this.repository.save(customer);
        return this.mapper.mapEntityFromDTO(savedCustomer);
    }

    private void verifyIfIsAlreadyRegistered(String code) throws InvalidClientException {
        Customer optSaved = repository.findByIdentificationCode(code);
        if (optSaved != null) {
            throw new InvalidClientException(String.format("Cliente com CPF %s já cadastrado no sistema.", code));
        }
    }

    public CustomerDTO save(CustomerDTO customerDTO) throws InvalidClientException {
        Customer customer = this.repository.findByIdentificationCode(customerDTO.getCpf());
        if (customer != null) {
            customer.setName(customerDTO.getNome());
            customer.setBirthDate(customerDTO.getDataNascimento());
            customer.setEmail(customerDTO.getEmail());
            this.repository.save(customer);
            return customerDTO;
        }
        throw new InvalidClientException("Cliente não esta cadastrado na sistema");
    }

    public CustomerDTO findByIdentificationCode(String code) {
        Customer customer = this.repository.findByIdentificationCode(code);
        return this.mapper.mapEntityFromDTO(customer);
    }

    public List<CustomerDTO> listCustomers() {
        return this.repository.findAll()
                .stream().map(customer -> this.mapper.mapEntityFromDTO(customer))
                .collect(Collectors.toList());
    }
}
