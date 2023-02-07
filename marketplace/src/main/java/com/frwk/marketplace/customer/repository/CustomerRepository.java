package com.frwk.marketplace.customer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.frwk.marketplace.customer.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Customer findByIdentificationCode(String code);


}
