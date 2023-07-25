package com.seine.productcatalog.controller;

import com.seine.productcatalog.model.Product;
import com.seine.productcatalog.service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Arrays;
import java.util.List;


import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ProductController.class)
public class ProductControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    ProductService productService;

    @Test
    public void testList() throws Exception {
        Product product1 = new Product("1","Product 1", new String[]{"url1", "url2"}, "Description 1", new String[]{"tag1", "tag2"});
        Product product2 = new Product("2","Product 2", new String[]{"url3", "url4"}, "Description 2", new String[]{"tag3", "tag4"});

        List<Product> products = Arrays.asList(product1, product2);

        given(productService.list()).willReturn(products);

        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"id\":\"1\", \"name\":\"Product 1\", \"imageUrls\":[\"url1\", \"url2\"], \"description\":\"Description 1\", \"tags\":[\"tag1\", \"tag2\"]}, " +
                        "{\"id\":\"2\", \"name\":\"Product 2\", \"imageUrls\":[\"url3\", \"url4\"], \"description\":\"Description 2\", \"tags\":[\"tag3\", \"tag4\"]}]"));
    }

    @Test
    public void testGet() throws Exception {
        Product product = new Product("1","Product 1", new String[]{"url1", "url2"}, "Description 1", new String[]{"tag1", "tag2"});
        given(productService.get("1")).willReturn(product);

        mockMvc.perform(get("/products/1"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":\"1\", \"name\":\"Product 1\", \"imageUrls\":[\"url1\", \"url2\"], \"description\":\"Description 1\", \"tags\":[\"tag1\", \"tag2\"]}"));
    }

    @Test
    public void testCreate() throws Exception {
        Product product = new Product("Product 1", new String[]{"url1", "url2"}, "Description 1", new String[]{"tag1", "tag2"});
        String productJson = objectMapper.writeValueAsString(product);

        given(productService.create(Mockito.any(Product.class))).willReturn(product);

        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productJson))
                .andExpect(status().isCreated())
                .andExpect(content().json("{\"name\":\"Product 1\", \"imageUrls\":[\"url1\", \"url2\"], \"description\":\"Description 1\", \"tags\":[\"tag1\", \"tag2\"]}"));
    }

    @Test
    public void testEdit() throws Exception {
        Product product = new Product("1","Product 1", new String[]{"url1", "url2"}, "Description 1", new String[]{"tag1", "tag2"});
        String productJson = objectMapper.writeValueAsString(product);

        given(productService.edit(Mockito.anyString(), Mockito.any(Product.class))).willReturn(product);

        mockMvc.perform(put("/products/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productJson))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":\"1\", \"name\":\"Product 1\", \"imageUrls\":[\"url1\", \"url2\"], \"description\":\"Description 1\", \"tags\":[\"tag1\", \"tag2\"]}"));
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete("/products/1"))
                .andExpect(status().isOk());

        Mockito.verify(productService, times(1)).delete("1");
    }

}
