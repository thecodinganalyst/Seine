package com.seine.productcatalog.repository;

import com.seine.productcatalog.MongoDBTestContainerConfig;
import com.seine.productcatalog.model.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;

@DataMongoTest
@ContextConfiguration(classes = MongoDBTestContainerConfig.class)
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    void findAllByIdIn() {
        // Given
        Product product1 = new Product("Product 1", new String[]{"url1"}, "Desc 1", new String[]{"tag1"});
        Product product2 = new Product( "Product 2", new String[]{"url2"}, "Desc 2", new String[]{"tag2"});

        Product saved1 = productRepository.save(product1);
        Product saved2 = productRepository.save(product2);

        // When
        List<Product> products = productRepository.findAllByIdIn(List.of(saved1.getId(), saved2.getId()));

        // Then
        assertThat(products.size(), equalTo(2));
        assertThat(products, containsInAnyOrder(saved1, saved2));
    }
}
