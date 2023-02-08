package com.frwk.marketplace.shoppingCart.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.frwk.marketplace.core.exceptions.InvalidClientException;
import com.frwk.marketplace.core.exceptions.handler.RestExceptionHandler;
import com.frwk.marketplace.customer.dto.CustomerDTO;
import com.frwk.marketplace.customer.mappers.CustomerMapper;
import com.frwk.marketplace.customer.model.Customer;
import com.frwk.marketplace.customer.service.CustomerService;
import com.frwk.marketplace.shoppingCart.dto.ShoppingCartCreatedDTO;
import com.frwk.marketplace.shoppingCart.model.ShoppingCart;
import com.frwk.marketplace.shoppingCart.model.enums.StatusCart;
import com.frwk.marketplace.shoppingCart.repository.ShoppingCartRepository;
import com.frwk.marketplace.util.Creators;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class ShoppingCartServiceTest {

    private MockMvc mockMvc;

    @InjectMocks
    private ShoppingCartService shopCartService;
    
    @Mock
    private CustomerService customerService;

    @Mock
    private ShoppingCartRepository  shopCartRepository;

    @Mock
    private CustomerMapper customerMapper;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(
                shopCartService)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setControllerAdvice(new RestExceptionHandler())
                .setViewResolvers((s, locale) -> new MappingJackson2JsonView())
                .build();
    }

    @Test
    void whenCreateShoppingCartCartListNotEmpty() throws Exception {
        CustomerDTO customerDTO = Creators.createValidCustomerDTO();
        ShoppingCart cart = Creators.shoppingCartNewCart(StatusCart.OPEN);
        ShoppingCartCreatedDTO carCreattDTO = Creators.shoppingCartDTOCreateNewCart(StatusCart.OPEN,cart.getId());
        Mockito.when(this.customerService.findByIdentificationCodeEntity(ArgumentMatchers.any())).thenReturn(cart.getCustomer());
        Mockito.when(this.shopCartRepository.save(ArgumentMatchers.any())).thenReturn(cart);
        Mockito.when(this.customerService.saveEntity(ArgumentMatchers.any())).thenReturn(cart.getCustomer());
        ShoppingCartCreatedDTO retorno = this.shopCartService.createShoppingCart(customerDTO);
         assertEquals(carCreattDTO.getStatus(), retorno.getStatus());
         assertEquals(carCreattDTO.getIdCarrinho(), retorno.getIdCarrinho());
    }

    @Test
    void whenCreateShoppingCartCartListIsEmpty() throws Exception {
        Customer customer = Creators.createCustomer();
        CustomerDTO customerDTO = Creators.createValidCustomerDTO();
        ShoppingCart cart = Creators.shoppingCartNewCart(StatusCart.OPEN_NEW);
        ShoppingCartCreatedDTO carCreattDTO = Creators.shoppingCartDTOCreateNewCart(StatusCart.OPEN_NEW, cart.getId());
        Mockito.when(this.customerService.findByIdentificationCodeEntity(ArgumentMatchers.any()))
                .thenReturn(customer);
        Mockito.when(this.shopCartRepository.save(ArgumentMatchers.any())).thenReturn(cart);
        Mockito.when(this.customerService.saveEntity(ArgumentMatchers.any())).thenReturn(cart.getCustomer());
        ShoppingCartCreatedDTO retorno = this.shopCartService.createShoppingCart(customerDTO);
        assertEquals(carCreattDTO.getStatus(), retorno.getStatus());
        assertEquals(carCreattDTO.getIdCarrinho(), retorno.getIdCarrinho());
    }

    @Test
    void whenCreateShoppingCartCartCustomerNull() throws Exception {
        CustomerDTO customerDTO = Creators.createValidCustomerDTO();
        ShoppingCart cart = Creators.shoppingCartNewCart(StatusCart.OPEN_NEW);
        ShoppingCartCreatedDTO carCreattDTO = Creators.shoppingCartDTOCreateNewCart(StatusCart.OPEN_NEW, cart.getId());
        Customer customer = Creators.createCustomer();
        Mockito.when(this.customerService.findByIdentificationCodeEntity(ArgumentMatchers.any()))
                .thenReturn(null, customer);

                Mockito.when(this.shopCartRepository.save(ArgumentMatchers.any())).thenReturn(cart);
        Mockito.when(this.customerService.saveEntity(ArgumentMatchers.any())).thenReturn(cart.getCustomer());
        ShoppingCartCreatedDTO retorno = this.shopCartService.createShoppingCart(customerDTO);
        assertEquals(carCreattDTO.getStatus(), retorno.getStatus());
        assertEquals(carCreattDTO.getIdCarrinho(), retorno.getIdCarrinho());
    }
    
    @Test
    void whenCreateShoppingCartCartCustomerCartNull() throws Exception {
        CustomerDTO customerDTO = Creators.createValidCustomerDTO();
        ShoppingCart cart = Creators.shoppingCartNewCart(StatusCart.OPEN_NEW);
        ShoppingCartCreatedDTO carCreattDTO = Creators.shoppingCartDTOCreateNewCart(StatusCart.OPEN_NEW, cart.getId());
        Customer customer = Creators.createCustomer();
        Mockito.when(this.customerService.findByIdentificationCodeEntity(ArgumentMatchers.any()))
                .thenReturn(null, customer);

        Mockito.when(this.shopCartRepository.save(ArgumentMatchers.any())).thenReturn(null);
        Mockito.when(this.customerService.saveEntity(ArgumentMatchers.any())).thenReturn(cart.getCustomer());
         assertThrows(InvalidClientException.class,
               () -> this.shopCartService.createShoppingCart(customerDTO));
   }


}
