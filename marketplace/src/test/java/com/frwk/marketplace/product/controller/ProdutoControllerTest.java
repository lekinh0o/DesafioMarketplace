package com.frwk.marketplace.product.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
import com.frwk.marketplace.product.dto.ProductDTO;
import com.frwk.marketplace.product.service.ProductService;
import com.frwk.marketplace.util.Creators;

@ExtendWith(MockitoExtension.class)
public class ProdutoControllerTest {

    public final static String API = "/api/v1/marketplace/produto";
    private MockMvc mockMvc;

    @InjectMocks
    private ProductController productController;

    @Mock
    private ProductService productService;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(productController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setControllerAdvice(new RestExceptionHandler())
                .setViewResolvers((s, locale) -> new MappingJackson2JsonView())
                .build();
    }

    
    @Test
    void whenFindAllProductGet() throws Exception {
        List<ProductDTO> list = new ArrayList<ProductDTO>();
        list.add(Creators.createProductDTO());
        Mockito.when(this.productService.findAll()).thenReturn(list);

        this.mockMvc.perform(get(API)
                .contentType(MediaType.APPLICATION_JSON)
                .content(""))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(list.get(0).getId()))
                .andExpect(jsonPath("$[0].nome").value(list.get(0).getName()))
                .andExpect(jsonPath("$[0].preco").value(list.get(0).getPrice().toString()));

    }
    
    
}
