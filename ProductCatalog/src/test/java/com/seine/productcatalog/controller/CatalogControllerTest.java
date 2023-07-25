package com.seine.productcatalog.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.seine.productcatalog.MongoDBTestContainerConfig;
import com.seine.productcatalog.model.Product;
import com.seine.productcatalog.service.ProductService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.GraphQlTest;
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureGraphQlTester;
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureHttpGraphQlTester;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.graphql.test.tester.GraphQlTester;
import org.springframework.graphql.test.tester.HttpGraphQlTester;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.any;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@GraphQlTest(CatalogController.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CatalogControllerIntegrationTest {

    @Autowired
    GraphQlTester graphQlTester;

    @MockBean
    ProductService productService;

    Product saved1;
    Product saved2;

//    @BeforeAll
//    void setup() throws Exception {
//        Product product1 = new Product("Product 1", new String[]{"url1"}, "Desc 1", new String[]{"tag1"});
//        Product product2 = new Product( "Product 2", new String[]{"url2"}, "Desc 2", new String[]{"tag2"});

//        String saved1Json = mockMvc.perform(post("/products")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(product1)))
//                .andExpect(status().isOk())
//                .andReturn()
//                .getResponse()
//                .getContentAsString();
//        saved1 = objectMapper.readValue(saved1Json, Product.class);
//        String saved2Json = mockMvc.perform(post("/products")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(product2)))
//                .andExpect(status().isOk())
//                .andReturn()
//                .getResponse()
//                .getContentAsString();
//        saved2 = objectMapper.readValue(saved2Json, Product.class);
//    }


    @Test
    void whenSearchById_thenReturnProducts(){
        Product product1 = new Product("Product 1", new String[]{"url1"}, "Desc 1", new String[]{"tag1"});
        Product product2 = new Product( "Product 2", new String[]{"url2"}, "Desc 2", new String[]{"tag2"});

        given(productService.searchByIds(List.of("1", "2"))).willReturn(List.of(product1, product2));

        String query = "{\n" +
                "    searchByIds(ids: [\"%s\", \"%s\"]){\n" +
                "        name\n" +
                "        description\n" +
                "        tags\n" +
                "    }\n" +
                "}";
        List<Product> productList = graphQlTester.document(String.format(query, "1", "2"))
                .execute()
                .errors()
                .verify()
                .path("searchByIds")
                .entityList(Product.class)
                .get();
        assertThat(productList, containsInAnyOrder(product1, product2));
    }
}
