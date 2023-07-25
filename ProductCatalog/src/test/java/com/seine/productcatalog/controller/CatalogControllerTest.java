package com.seine.productcatalog.controller;

import com.seine.productcatalog.model.Product;
import com.seine.productcatalog.service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.GraphQlTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.graphql.test.tester.GraphQlTester;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.mockito.BDDMockito.given;

@GraphQlTest(CatalogController.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CatalogControllerTest {

    @Autowired
    GraphQlTester graphQlTester;

    @MockBean
    ProductService productService;


    @Test
    void whenSearchById_thenReturnProducts(){
        Product product1 = new Product("Product 1", null, "Desc 1", new String[]{"tag1"});
        Product product2 = new Product( "Product 2", null, "Desc 2", new String[]{"tag2"});

        given(productService.searchByIds(List.of("1", "2"))).willReturn(List.of(product1, product2));

        String query = """
                {
                    searchByIds(ids: ["%s", "%s"]){
                        name
                        description
                        tags
                    }
                }""";
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
