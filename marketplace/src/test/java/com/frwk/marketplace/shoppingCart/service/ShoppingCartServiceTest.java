package com.frwk.marketplace.shoppingCart.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Optional;

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

import com.frwk.marketplace.core.exceptions.InvalidCartException;
import com.frwk.marketplace.core.exceptions.InvalidClientException;
import com.frwk.marketplace.core.exceptions.handler.RestExceptionHandler;
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
    private ShoppingCartItensService itensService;
    
    @Mock
    private ProductService productService;
    @Mock
    private ShoppingCartRepository  shopCartRepository;

    @Mock
    private CustomerMapper customerMapper;
    
    @Mock
    private ShoppingCartItensMapper shoppingCartItensMapper;

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
        Customer customer = Creators.createCustomer();
        Mockito.when(this.customerService.findByIdentificationCodeEntity(ArgumentMatchers.any()))
                .thenReturn(null, customer);

        Mockito.when(this.shopCartRepository.save(ArgumentMatchers.any())).thenReturn(null);
        Mockito.when(this.customerService.saveEntity(ArgumentMatchers.any())).thenReturn(cart.getCustomer());
         assertThrows(InvalidClientException.class,
               () -> this.shopCartService.createShoppingCart(customerDTO));
   }

   @Test
   void whenPostIncludeproductInCart() throws Exception {
       Product produto = Creators.createProduto();
       ShoppingCart shoppingCart = Creators.shoppingCartAndProductCart(StatusCart.OPEN);
       Mockito.when(this.shopCartRepository.findById(ArgumentMatchers.any())).thenReturn(Optional.of(shoppingCart));
       Mockito.when(this.productService.findById(ArgumentMatchers.any())).thenReturn(Optional.of(produto));
       Mockito.when(this.itensService.findAllItensByShoppingCart(ArgumentMatchers.any()))
               .thenReturn(shoppingCart.getItens());
       ProductCreateDTO productCreateDTO = Creators.productCreateDTO(shoppingCart);
      this.shopCartService.includeproductInCart(productCreateDTO);
       Mockito.verify(this.itensService, Mockito.times(1)).saveCartItem(shoppingCart.getItens().get(0));
   }

   @Test
   void whenPostIncludeproductInCartNewAdd() throws Exception {
       Product produto = Creators.createProdutoNewAdd();
       ShoppingCart shoppingCart = Creators.shoppingCartAndProductCart(StatusCart.OPEN);
       ProductCreateDTO productCreateDTO = Creators.productCreateDTO(shoppingCart);

       ShoppingCartItens iten = ShoppingCartItens.builder().shoppingCart(shoppingCart).product(produto).quantity(productCreateDTO.getQuantidade()).build();
       Mockito.when(this.shopCartRepository.findById(ArgumentMatchers.any())).thenReturn(Optional.of(shoppingCart));
       Mockito.when(this.productService.findById(ArgumentMatchers.any())).thenReturn(Optional.of(produto));
       Mockito.when(this.itensService.findAllItensByShoppingCart(ArgumentMatchers.any()))
               .thenReturn(shoppingCart.getItens());

        Mockito.when(this.itensService.saveCartItem(ArgumentMatchers.any()))
               .thenReturn(iten);
     this.shopCartService.includeproductInCart(productCreateDTO);
       

   }

   @Test
   void whenPostincludeproductInCartProductErro() throws Exception {
       ShoppingCart shoppingCart = Creators.shoppingCartAndProductCart(StatusCart.OPEN);
       Mockito.when(this.shopCartRepository.findById(ArgumentMatchers.any())).thenReturn(Optional.of(shoppingCart));
       ProductCreateDTO productCreateDTO = Creators.productCreateDTO(shoppingCart);
       
       assertThrows(InvalidCartException.class,
               () -> this.shopCartService.includeproductInCart(productCreateDTO));
   }


   @Test
   void whenPostincludeproductInCarErro() throws Exception {
       ShoppingCart shoppingCart = Creators.shoppingCartAndProductCart(StatusCart.OPEN);
       ProductCreateDTO productCreateDTO = Creators.productCreateDTO(shoppingCart);
       assertThrows(InvalidCartException.class,
               () -> this.shopCartService.includeproductInCart(productCreateDTO));
   }

   @Test
   void whenPostSloseShoppingCart() throws Exception {
       Product produto = Creators.createProduto();
       ShoppingCart shoppingCart = Creators.shoppingCartAndProductCart(StatusCart.OPEN);
       ShoppingCartCloseDTO shoppingCartCloseDTO = Creators.shoppingCartCloseDTO(shoppingCart);
       Mockito.when(this.productService.findById(ArgumentMatchers.any())).thenReturn(Optional.of(produto));
       Mockito.when(this.shopCartRepository.findById(ArgumentMatchers.any())).thenReturn(Optional.of(shoppingCart));
       Mockito.when(this.itensService.findAllItensByShoppingCart(ArgumentMatchers.any()))
               .thenReturn(shoppingCart.getItens());
       Mockito.when(this.customerMapper.mapEntityFromDTO(ArgumentMatchers.any())).thenReturn(shoppingCartCloseDTO.getCustomer());
       Mockito.when(this.shoppingCartItensMapper.mapEntityFromDTO(ArgumentMatchers.any())).thenReturn(shoppingCartCloseDTO.getItens().get(0));
               ShoppingCartCloseDTO retorno = this.shopCartService.closeShoppingCart(shoppingCart.getId().toString());
      
       assertEquals(retorno ,shoppingCartCloseDTO);
   }

}
