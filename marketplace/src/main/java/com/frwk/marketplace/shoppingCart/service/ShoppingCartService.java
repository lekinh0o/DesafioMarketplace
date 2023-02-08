package com.frwk.marketplace.shoppingCart.service;

import java.util.ArrayList;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.frwk.marketplace.core.exceptions.InvalidClientException;
import com.frwk.marketplace.customer.dto.CustomerDTO;
import com.frwk.marketplace.customer.mappers.CustomerMapper;
import com.frwk.marketplace.customer.model.Customer;
import com.frwk.marketplace.customer.service.CustomerService;
import com.frwk.marketplace.shoppingCart.dto.ShoppingCartCreatedDTO;
import com.frwk.marketplace.shoppingCart.model.ShoppingCart;
import com.frwk.marketplace.shoppingCart.model.enums.StatusCart;
import com.frwk.marketplace.shoppingCart.repository.ShoppingCartRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ShoppingCartService {

    private ShoppingCartRepository repository;

    private CustomerService customerService;

    private CustomerMapper customerMapper;

    public ShoppingCartCreatedDTO createShoppingCart(CustomerDTO dto) throws InvalidClientException {
        Customer customer = this.customerService.findByIdentificationCodeEntity(dto.getCpf());
        if (customer == null) {
            this.customerMapper.mapDTOFromEntity(this.customerService.createCustomer(dto));
            customer = this.customerService.findByIdentificationCodeEntity(dto.getCpf());
        }
        ShoppingCart cart = this.isShoppingCartOpen(customer);
        if (cart != null) {
            return ShoppingCartCreatedDTO.builder().status(cart.getStatus().name())
                    .idCarrinho(cart.getId().toString()).build();
        }
        throw new InvalidClientException("Cliente  não foi informado");

    }

    private ShoppingCart isShoppingCartOpen(Customer customer) throws InvalidClientException {
        ShoppingCart cart = null;
        if (customer.getShoppingCart() != null && !customer.getShoppingCart().isEmpty()) {
            cart = customer.getShoppingCart().stream()
                    .filter(c -> c.getStatus().equals(StatusCart.OPEN))
                    .findFirst().orElse(null);
        }
        if (cart != null) {
            return cart;
        }
        cart = this.repository.save(ShoppingCart.builder().id(
                UUID.randomUUID()).customer(customer).status(StatusCart.OPEN_NEW).build());
        ArrayList<ShoppingCart> carts = new ArrayList();
        carts.add(cart);
        customer.setShoppingCart(carts);
        this.customerService.saveEntity(customer);
        return cart;
    }

}
