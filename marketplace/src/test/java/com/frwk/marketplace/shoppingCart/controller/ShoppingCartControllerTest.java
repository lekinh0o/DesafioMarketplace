package com.frwk.marketplace.shoppingCart.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.frwk.marketplace.core.exceptions.InvalidCartException;
import com.frwk.marketplace.core.exceptions.handler.RestExceptionHandler;
import com.frwk.marketplace.customer.dto.CustomerDTO;
import com.frwk.marketplace.product.dto.ProductCreateDTO;
import com.frwk.marketplace.shoppingCart.dto.ShoppingCartCloseDTO;
import com.frwk.marketplace.shoppingCart.dto.ShoppingCartCreatedDTO;
import com.frwk.marketplace.shoppingCart.model.ShoppingCart;
import com.frwk.marketplace.shoppingCart.model.enums.StatusCart;
import com.frwk.marketplace.shoppingCart.service.ShoppingCartItensService;
import com.frwk.marketplace.shoppingCart.service.ShoppingCartService;
import com.frwk.marketplace.util.Creators;
import com.frwk.marketplace.util.MappingJsonConvertion;

@ExtendWith(MockitoExtension.class)
public class ShoppingCartControllerTest {

    public final static String API = "/api/v1/marketplace/carrinho";
    private MockMvc mockMvc;

    @InjectMocks
    private ShoppingCartController shopCartController;

    @Mock
    private ShoppingCartService shopCartService;
    
    @Mock
    private ShoppingCartItensService itensService;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(shopCartController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setControllerAdvice(new RestExceptionHandler())
                .setViewResolvers((s, locale) -> new MappingJackson2JsonView())
                .build();
    }

    @Test
    void whenCreateShoppingCartPostIsCreated() throws Exception {
        CustomerDTO customer = Creators.createValidCustomerDTO();
        ShoppingCartCreatedDTO cart = Creators.shoppingCartDTOCreate(StatusCart.OPEN_NEW);
        Mockito.when(this.shopCartService.createShoppingCart(ArgumentMatchers.any())).thenReturn(cart);
        this.mockMvc.perform(post(API)
                .contentType(MediaType.APPLICATION_JSON)
                .content(MappingJsonConvertion.objectMapper(customer)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idCarrinho").value(cart.getIdCarrinho()));

    }

    @Test
    void whenCreateShoppingCartPostIsOk() throws Exception {
        CustomerDTO customer = Creators.createValidCustomerDTO();
        ShoppingCartCreatedDTO cart = Creators.shoppingCartDTOCreate(StatusCart.OPEN);
        Mockito.when(this.shopCartService.createShoppingCart(ArgumentMatchers.any())).thenReturn(cart);
        this.mockMvc.perform(post(API)
                .contentType(MediaType.APPLICATION_JSON)
                .content(MappingJsonConvertion.objectMapper(customer)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idCarrinho").value(cart.getIdCarrinho()));

    }
    
    @Test
    void whenPostCustomerCpfInvalide() throws Exception {
        CustomerDTO customerDTO = Creators.createCustomerDTOInvalidCPF();
        this.mockMvc.perform(post(API)
                .contentType(MediaType.APPLICATION_JSON)
                .content(MappingJsonConvertion.objectMapper(customerDTO)))
                .andExpect(status().isPreconditionFailed())
                .andExpect(jsonPath("$.type").value("ModelValidationException"))
                .andExpect(jsonPath("$.message").value("Dados enviados inválidos"))
                .andExpect(jsonPath("$.erros.[0].field").value("cpf"))
                .andExpect(jsonPath("$.erros.[0].error").value("O campo 'cpf' é obrigatório"));
    }

    @Test
    void whenPostincludeproductInCart() throws Exception {
        Mockito.doNothing().when(this.shopCartService).includeproductInCart(ArgumentMatchers.any());
        ShoppingCart shoppingCart = Creators.shoppingCartAndProductCart(StatusCart.OPEN); 
        ProductCreateDTO productCreateDTO = Creators.productCreateDTO(shoppingCart);

        this.mockMvc.perform(post(API+"/"+"produto")
                .contentType(MediaType.APPLICATION_JSON)
                .content(MappingJsonConvertion.objectMapper(
                        productCreateDTO)))
                .andExpect(status().isOk());
    }

    @Test
    void whencloseShoppingCart() throws Exception {
        ShoppingCart shoppingCart = Creators.shoppingCartAndProductCart(StatusCart.OPEN); 
        ShoppingCartCloseDTO shoppingCartCloseDTO = Creators.shoppingCartCloseDTO(shoppingCart);
         Mockito.when(this.shopCartService.closeShoppingCart(ArgumentMatchers.any())).thenReturn(shoppingCartCloseDTO);
        this.mockMvc.perform(post(API + "/fechar/"+shoppingCart.getId().toString())
                .contentType(""))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idCarrinho").value(shoppingCart.getId().toString()))
                .andExpect(jsonPath("$.cliente.cpf").value(shoppingCart.getCustomer().getIdentificationCode()))
                .andExpect(jsonPath("$.itens.[0].quantidade").value((10)))
                .andExpect(jsonPath("$.itens.[0].produto.nome").value("BALA"));
    }
    
    @Test
    void whenPostCalledToCloseValidClosedCart() throws Exception {
        ShoppingCart shoppingCart = Creators.shoppingCartAndProductCart(StatusCart.OPEN);
        Mockito.when(this.shopCartService.closeShoppingCart((ArgumentMatchers.any())))
                .thenThrow(new InvalidCartException("Carrinho informado ja se encontra fechado"));

        this.mockMvc.perform(post(API + "/fechar/" + shoppingCart.getId().toString()))
                .andExpect(status().isPreconditionFailed())
                .andExpect(jsonPath("$.type").value("InvalidCartException"))
                .andExpect(jsonPath("$.message").value("Carrinho informado ja se encontra fechado"));
    }

    @Test
    void whenPostCalledToCloseInvalidCart() throws Exception {
        ShoppingCart shoppingCart = Creators.shoppingCartAndProductCart(StatusCart.OPEN);
        Mockito.when(this.shopCartService.closeShoppingCart((ArgumentMatchers.any())))
                .thenThrow(new InvalidCartException("Carrinho informado é inválido"));

        this.mockMvc.perform(post(API + "/fechar/" + shoppingCart.getId().toString()))
                .andExpect(status().isPreconditionFailed())
                .andExpect(jsonPath("$.type").value("InvalidCartException"))
                .andExpect(jsonPath("$.message").value ("Carrinho informado é inválido"));
    }

    @Test
    void whenPostCalledToCloseValidEmptyCart() throws Exception {
        ShoppingCart shoppingCart = Creators.shoppingCartAndProductCart(StatusCart.OPEN);
        Mockito.when(this.shopCartService.closeShoppingCart((ArgumentMatchers.any())))
                .thenThrow(new InvalidCartException("O carrinho não possui produtos"));

        this.mockMvc.perform(post(API + "/fechar/" + shoppingCart.getId().toString()))
                .andExpect(status().isPreconditionFailed())
                .andExpect(jsonPath("$.type").value("InvalidCartException"))
                .andExpect(jsonPath("$.message").value("O carrinho não possui produtos"));
    }
}
