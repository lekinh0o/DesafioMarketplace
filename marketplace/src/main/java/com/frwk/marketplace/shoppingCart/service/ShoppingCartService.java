package com.frwk.marketplace.shoppingCart.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.frwk.marketplace.core.exceptions.InvalidCartException;
import com.frwk.marketplace.core.exceptions.InvalidClientException;
import com.frwk.marketplace.customer.dto.CustomerDTO;
import com.frwk.marketplace.customer.mappers.CustomerMapper;
import com.frwk.marketplace.customer.model.Customer;
import com.frwk.marketplace.customer.service.CustomerService;
import com.frwk.marketplace.product.dto.ProductCreateDTO;
import com.frwk.marketplace.product.model.Product;
import com.frwk.marketplace.product.service.ProductService;
import com.frwk.marketplace.shoppingCart.dto.ShoppingCartCloseDTO;
import com.frwk.marketplace.shoppingCart.dto.ShoppingCartCreatedDTO;
import com.frwk.marketplace.shoppingCart.mapper.ShoppingCartItensMapper;
import com.frwk.marketplace.shoppingCart.model.ShoppingCart;
import com.frwk.marketplace.shoppingCart.model.ShoppingCartItens;
import com.frwk.marketplace.shoppingCart.model.enums.StatusCart;
import com.frwk.marketplace.shoppingCart.repository.ShoppingCartRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ShoppingCartService {

    private ShoppingCartRepository repository;

    private ShoppingCartItensService itensService;

    private CustomerService customerService;
    
    private ProductService productService;
    
    private CustomerMapper customerMapper;

    private ShoppingCartItensMapper shoppingCartItensMapper;

    public ShoppingCartCreatedDTO createShoppingCart(CustomerDTO dto) throws InvalidClientException {
        Customer customer = this.customerService.findByIdentificationCodeEntity(dto.getCpf());
        if (customer == null) {
            this.customerService.createCustomer(dto);
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
        if (customer.getShoppingCart() == null || customer.getShoppingCart().isEmpty()) {
            cart = this.repository.save(ShoppingCart.builder().id(
                    UUID.randomUUID()).customer(customer).status(StatusCart.OPEN_NEW).build());
            ArrayList<ShoppingCart> carts = new ArrayList();
            carts.add(cart);
            customer.setShoppingCart(carts);
            this.customerService.saveEntity(customer);
            return cart;
        }
        cart = customer.getShoppingCart().stream()
                .filter(c -> !c.getStatus().equals(StatusCart.CLOSED))
                .findFirst().orElse(null);
        cart.setStatus(StatusCart.OPEN);
        return this.repository.save(cart);

    }

    public void includeproductInCart(ProductCreateDTO productCreateDTO) throws InvalidCartException {
        ShoppingCart cart = this.shoppingCartIsPresentOrValid(productCreateDTO.getIdCarrinho());
        Product product = this.productIsPresentOrValid(Long.parseLong(productCreateDTO.getIdProduto()));

        List<ShoppingCartItens> itens = this.itensService.findAllItensByShoppingCart(cart);
        ShoppingCartItens existProductInCart = itens.stream()
                .filter(item -> product.getId().equals(item.getProduct().getId())).findFirst().orElse(null);
        if (existProductInCart != null) {
            existProductInCart.setQuantity(existProductInCart.getQuantity() + productCreateDTO.getQuantidade());
            this.itensService.saveCartItem(existProductInCart);
            return;
        }
        this.itensService.saveCartItem(ShoppingCartItens.builder().shoppingCart(cart).product(product).quantity(
                productCreateDTO.getQuantidade()).build());
    }

     ShoppingCart shoppingCartIsPresentOrValid(String idCart) throws InvalidCartException {
        Optional<ShoppingCart> optCart;
        try {
            optCart = this.repository.findById(UUID.fromString(idCart));

        } catch (IllegalArgumentException e) {
            throw new InvalidCartException("Carrinho informado é inválido");
        }

        if (!optCart.isPresent()) {
            throw new InvalidCartException("Carrinho informado é inválido");
        }

        if (optCart.get().getStatus().equals(StatusCart.CLOSED)) {
            throw new InvalidCartException("Carrinho informado ja se encontra fechado");
        }

        return optCart.get();
    }

    public Product productIsPresentOrValid(Long idProduct) throws InvalidCartException {
        Optional<Product> optProduct;
        try {
            optProduct = this.productService.findById(idProduct);

        } catch (IllegalArgumentException e) {
            throw new InvalidCartException("Produto informado é inválido");
        }

        if (!optProduct.isPresent()) {
            throw new InvalidCartException("Produto informado é inválido");
        }

        return optProduct.get();
    }

    public ShoppingCartCloseDTO closeShoppingCart(String idShoppingCart) throws InvalidCartException {
        ShoppingCart cart = this.shoppingCartIsPresentOrValid(idShoppingCart);
        List<ShoppingCartItens> itens = this.itensService.findAllItensByShoppingCart(cart);
        if (itens == null || itens.isEmpty()) {
            throw new InvalidCartException("O carrinho não possui produtos.");
        }
        cart.setStatus(StatusCart.CLOSED);
        this.repository.save(cart);
        return ShoppingCartCloseDTO.builder().idShoppingCart(cart.getId().toString())
                .itens(itens.stream().map(item -> this.shoppingCartItensMapper.mapEntityFromDTO(item))
                        .collect(Collectors.toList()))
                .customer(this.customerMapper.mapEntityFromDTO(cart.getCustomer())).build();
    }

}
