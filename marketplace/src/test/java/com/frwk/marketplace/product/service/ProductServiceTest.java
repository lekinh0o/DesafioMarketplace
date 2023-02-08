package com.frwk.marketplace.product.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

import com.frwk.marketplace.core.exceptions.handler.RestExceptionHandler;
import com.frwk.marketplace.product.dto.ProductDTO;
import com.frwk.marketplace.product.mapper.ProductMapper;
import com.frwk.marketplace.product.model.Product;
import com.frwk.marketplace.product.repository.ProductRepository;
import com.frwk.marketplace.util.Creators;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class ProductServiceTest {

    private MockMvc mockMvc;

    @InjectMocks
    private ProductService productService;


    @Mock
    private ProductRepository  productRepository;

    @Mock
    private ProductMapper productMapper;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(
                productService)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setControllerAdvice(new RestExceptionHandler())
                .setViewResolvers((s, locale) -> new MappingJackson2JsonView())
                .build();
    }

    @Test
    void whenlistProducts() throws Exception {
        List<Product> ProductsList = new ArrayList<Product>();
        ProductsList.add(Creators.createProduct());
        ProductDTO ProductDTO = Creators.createProductDTO();
        Mockito.when(this.productRepository.findAll()).thenReturn(ProductsList);
        Mockito.when(this.productMapper.mapEntityFromDTO(ArgumentMatchers.any())).thenReturn(ProductDTO);
         assertEquals(ProductsList.size(), this.productService.findAll().size()); 
    }
}
