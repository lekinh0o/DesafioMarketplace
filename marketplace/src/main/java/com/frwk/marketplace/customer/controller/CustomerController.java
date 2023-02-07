package com.frwk.marketplace.customer.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.frwk.marketplace.core.exceptions.InvalidClientException;
import com.frwk.marketplace.customer.dto.CustomerDTO;
import com.frwk.marketplace.customer.service.CustomerService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1/marketplace/cliente")
@AllArgsConstructor
public class CustomerController implements CustomerApi {
    @Autowired
    private final CustomerService service;

    @Override
    @PostMapping()
    public ResponseEntity<CustomerDTO> createCustomer(@RequestBody @Validated CustomerDTO customerDTO)
            throws InvalidClientException {
        return new ResponseEntity<>(this.service.createCustomer(customerDTO), HttpStatus.CREATED);
    }

    @Override
    @PutMapping
    public ResponseEntity<CustomerDTO> updateCustomer(@RequestBody @Validated CustomerDTO customerDTO)
            throws InvalidClientException {
        return new ResponseEntity<>(this.service.save(customerDTO), HttpStatus.ACCEPTED);
    }

    @Override
    @GetMapping("/{cpf}")
    public ResponseEntity<CustomerDTO> findByCpf(@PathVariable String cpf) throws InvalidClientException {
        return new ResponseEntity<>(this.service.findByIdentificationCode(cpf), HttpStatus.OK);
    }

    @Override
    @GetMapping
    public ResponseEntity<List<CustomerDTO>> listCustomers() {
        return new ResponseEntity<>(this.service.listCustomers(), HttpStatus.OK);
    }

}
