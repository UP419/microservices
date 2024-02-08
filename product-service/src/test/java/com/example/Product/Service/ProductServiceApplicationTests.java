package com.example.Product.Service;

import com.example.Product.Service.dto.ProductRequest;
import com.example.Product.Service.model.Product;
import com.example.Product.Service.repository.ProductRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
@PropertySource("classpath:application-test.properties")
class ProductServiceApplicationTests {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private static Environment env;

    @Value("${jdbc.driverClassName}")
    private String driverClassName;

    @Value("jdbc.url")
    private String jdbcUrl;

    @Value("jdbc.username")
    private String username;

    @Value("jdbc.password")
    private String password;

    @PostConstruct
    public void init() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl(jdbcUrl);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
    }

    @Test
    void saveProductTest() throws Exception {
        int sizeBefore = productRepository.findAll().size();
        ProductRequest request = initializeProductRequest();
        String requestAsString = objectMapper.writeValueAsString(request);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/product/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestAsString))
                .andExpect(status().isCreated());
        int sizeAfter = productRepository.findAll().size();
        Assertions.assertEquals(sizeAfter, sizeBefore + 1);
    }

    @Test
    void saveProductTest2() {
        int sizeBefore = productRepository.findAll().size();
        Product product = Product.builder()
                .name("Iphone")
                .price(1500.0)
                .description("phone")
                .build();
        productRepository.save(product);
        int sizeAfter = productRepository.findAll().size();
        Assertions.assertEquals(sizeAfter, sizeBefore + 1);
    }


    @Test
    void exceptionTest() throws Exception {
        ProductRequest request = ProductRequest.builder()
                .description("This is test product")
                .price(15.0)
                .build();
        String requestAsString = objectMapper.writeValueAsString(request);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/product/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestAsString))
                .andExpect(status().isBadRequest());
    }

    private ProductRequest initializeProductRequest() {
        return ProductRequest.builder()
                .name("testProduct")
                .description("This is test product")
                .price(15.0)
                .build();
    }

}
