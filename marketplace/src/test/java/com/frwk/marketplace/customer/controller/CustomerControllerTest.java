package com.frwk.marketplace.customer.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.frwk.marketplace.core.exceptions.handler.RestExceptionHandler;
import com.frwk.marketplace.customer.dto.CustomerDTO;
import com.frwk.marketplace.customer.service.CustomerService;
import com.frwk.marketplace.util.Creators;
import com.frwk.marketplace.util.MappingJsonConvertion;


@ExtendWith(MockitoExtension.class)
public class CustomerControllerTest {

    public final static String API = "/api/v1/marketplace/cliente";
    private MockMvc mockMvc;

    @InjectMocks
    private CustomerController customerController;

    @Mock
    private CustomerService customerService;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(customerController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setControllerAdvice(new RestExceptionHandler())
                .setViewResolvers((s, locale) -> new MappingJackson2JsonView())
                .build();
    }

    @Test
    void whenItsAVaildCustomerPost() throws Exception {

        CustomerDTO customerDTO = Creators.createValidCustomerDTO();
        Mockito.when(this.customerService.createCustomer(ArgumentMatchers.any())).thenReturn(customerDTO);
        this.mockMvc.perform(post(
                API)
                .contentType(MediaType.APPLICATION_JSON)
                .content(MappingJsonConvertion.objectMapper(customerDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome").value(customerDTO.getNome()))
                .andExpect(jsonPath("$.cpf").value(customerDTO.getCpf()))
                .andExpect(jsonPath("$.dataNascimento").value(customerDTO.getDataNascimento().toString()))
                .andExpect(jsonPath("$.email").value(customerDTO.getEmail()));

    }

    @Test
    void whenItsAVaildCustomerPut() throws Exception {

        CustomerDTO customerDTO = Creators.createValidCustomerDTO();
        Mockito.when(this.customerService.save(ArgumentMatchers.any())).thenReturn(customerDTO);

        this.mockMvc.perform(put(
                "/api/v1/marketplace/cliente")
                .contentType(MediaType.APPLICATION_JSON)
                .content(MappingJsonConvertion.objectMapper(customerDTO)))
                .andExpect(status().isAccepted()).andExpect(jsonPath("$.nome").value(customerDTO.getNome()))
                .andExpect(jsonPath("$.cpf").value(customerDTO.getCpf()))
                .andExpect(jsonPath("$.dataNascimento").value(customerDTO.getDataNascimento().toString()))
                .andExpect(jsonPath("$.email").value(customerDTO.getEmail()));

    }

    @Test
    void whenItsAVaildCustomerCPFGet() throws Exception {

        CustomerDTO customerDTO = Creators.createValidCustomerDTO();
        Mockito.when(this.customerService.findByIdentificationCode(ArgumentMatchers.any())).thenReturn(customerDTO);

        this.mockMvc.perform(get(API+"/"+customerDTO.getCpf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(MappingJsonConvertion.objectMapper(customerDTO)))
                .andExpect(status().isOk()).andExpect(jsonPath("$.nome").value(customerDTO.getNome()))
                .andExpect(jsonPath("$.cpf").value(customerDTO.getCpf()))
                .andExpect(jsonPath("$.dataNascimento").value(customerDTO.getDataNascimento().toString()))
                .andExpect(jsonPath("$.email").value(customerDTO.getEmail()));

    }
    
    @Test
    void whenItsAVaildCustomerListGet() throws Exception {
        List<CustomerDTO> customersList = new ArrayList<CustomerDTO>();
        customersList.add(Creators.createValidCustomerDTO());
        Mockito.when(this.customerService.listCustomers()).thenReturn(customersList);

        this.mockMvc.perform(get(API)
                .contentType(MediaType.APPLICATION_JSON)
                .content(""))
                .andExpect(status().isOk()).andExpect(jsonPath("$[0].nome").value(customersList.get(0).getNome()))
                .andExpect(jsonPath("$[0].cpf").value(customersList.get(0).getCpf()))
                .andExpect(jsonPath("$[0].dataNascimento").value(customersList.get(0).getDataNascimento().toString()))
                .andExpect(jsonPath("$[0].email").value(customersList.get(0).getEmail()));

    }
    
    @Test
    void whenPostCustomerCpfInvalide() throws Exception {
        CustomerDTO customerDTO = Creators.createCustomerDTOInvalidCPF();
        this.mockMvc.perform(post(API)
                .contentType(MediaType.APPLICATION_JSON)
                .content(MappingJsonConvertion.objectMapper(customerDTO)))
                .andExpect(status().isPreconditionFailed())
                .andExpect(jsonPath("$.type" ).value("ModelValidationException"))
                .andExpect(jsonPath("$.message").value( "Dados enviados inválidos"))
                .andExpect(jsonPath("$.erros.[0].field").value("cpf"))
                .andExpect(jsonPath("$.erros.[0].error").value("O campo 'cpf' é obrigatório"));
    }
    
}
