package com.frwk.marketplace.customer.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

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
import com.frwk.marketplace.customer.repository.CustomerRepository;
import com.frwk.marketplace.util.Creators;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class CustomerServiceTest {

    private MockMvc mockMvc;

    @InjectMocks
    private CustomerService customerService;


    @Mock
    private CustomerRepository  customerRepository;

    @Mock
    private CustomerMapper customerMapper;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(
                customerService)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setControllerAdvice(new RestExceptionHandler())
                .setViewResolvers((s, locale) -> new MappingJackson2JsonView())
                .build();
    }

    @Test
    void whenCreateCustomerCustomerServ() throws Exception {
        CustomerDTO customerDTO = Creators.createValidCustomerDTO();
        Customer customer = Creators.createCustomer();
        Mockito.when(this.customerMapper.mapDTOFromEntity(ArgumentMatchers.any())).thenReturn(customer);
        Mockito.when(this.customerRepository.findByIdentificationCode(ArgumentMatchers.any())).thenReturn(null);
        Mockito.when(this.customerRepository.save(ArgumentMatchers.any())).thenReturn(customer);
        Mockito.when(this.customerMapper.mapEntityFromDTO(ArgumentMatchers.any())).thenReturn(customerDTO);
        CustomerDTO returno  = this.customerService.createCustomer(customerDTO);
        assertEquals(customerDTO, returno);
    }

    @Test
    void whenVerifyIfIsAlreadyRegisteredCustomerServ() throws Exception {
        CustomerDTO customerDTO = Creators.createValidCustomerDTO();
        Customer customer = Creators.createCustomer();
        Mockito.when(this.customerRepository.findByIdentificationCode(ArgumentMatchers.any())).thenReturn(customer);
        assertThrows(InvalidClientException.class, () -> this.customerService.verifyIfIsAlreadyRegistered(customerDTO.getCpf()));
    }

    @Test
    void whenSaveCustomerServ() throws Exception {
        CustomerDTO customerDTO = Creators.createValidCustomerDTO();
        Customer customer = Creators.createCustomer();
        Mockito.when(this.customerMapper.mapEntityFromDTO(ArgumentMatchers.any())).thenReturn(customerDTO);
        Mockito.when(this.customerRepository.findByIdentificationCode(ArgumentMatchers.any())).thenReturn(customer);
        customerDTO.setNome("Davi");
        customerDTO.setEmail("Davi@email.com");
        Mockito.when(this.customerRepository.save(ArgumentMatchers.any())).thenReturn(customer);
        CustomerDTO retorno = this.customerService.save(customerDTO);
        assertTrue( retorno.getNome().equals(customerDTO.getNome()) && retorno.getEmail().equals(customerDTO.getEmail()));
        
    }
    
    @Test
    void whenSaveCustomerServNull() throws Exception {
        CustomerDTO customerDTO = Creators.createValidCustomerDTO();
        Mockito.when(this.customerMapper.mapEntityFromDTO(ArgumentMatchers.any())).thenReturn(customerDTO);
        Mockito.when(this.customerRepository.findByIdentificationCode(ArgumentMatchers.any())).thenReturn(null);
        customerDTO.setNome("Davi");
        customerDTO.setEmail("Davi@email.com");
        assertThrows(InvalidClientException.class,
                () -> this.customerService.save(customerDTO));
    }
    
    @Test
    void whenfindByIdentificationCodeNull() throws Exception {
        CustomerDTO customerDTO = Creators.createValidCustomerDTO();
        Mockito.when(this.customerRepository.findByIdentificationCode(ArgumentMatchers.any())).thenReturn(null);
        assertThrows(InvalidClientException.class,
                () -> this.customerService.findByIdentificationCode(customerDTO.getCpf()));
    }

    @Test
    void whenfindByIdentificationCode() throws Exception {
        CustomerDTO customerDTO = Creators.createValidCustomerDTO();
        Customer customer = Creators.createCustomer();
        Mockito.when(this.customerRepository.findByIdentificationCode(ArgumentMatchers.any())).thenReturn(customer);
        Mockito.when(this.customerMapper.mapEntityFromDTO(ArgumentMatchers.any())).thenReturn(customerDTO);
        assertEquals(customerDTO, this.customerService.findByIdentificationCode(customerDTO.getCpf()));
    }

    @Test
    void whenlistCustomers() throws Exception {
        List<Customer> customersList = new ArrayList<Customer>();
        customersList.add(Creators.createCustomer());
        CustomerDTO customerDTO = Creators.createValidCustomerDTO();
        Mockito.when(this.customerRepository.findAll()).thenReturn(customersList);
        Mockito.when(this.customerMapper.mapEntityFromDTO(ArgumentMatchers.any())).thenReturn(customerDTO);
         assertEquals(customersList.size(), this.customerService.listCustomers().size()); 
    }
}
