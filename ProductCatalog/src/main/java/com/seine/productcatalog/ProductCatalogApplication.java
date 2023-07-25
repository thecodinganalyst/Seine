package com.seine.productcatalog;

import com.seine.productcatalog.model.Product;
import com.seine.productcatalog.repository.ProductRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import java.util.List;

@SpringBootApplication
public class ProductCatalogApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductCatalogApplication.class, args);
    }

    @Bean
    @Profile("dev")
    ApplicationRunner initDatabase(ProductRepository productRepository){
        return args -> {
            List<Product> productList = productRepository.findAll();
            if(productList.size() == 0){
                productRepository.save(new Product("Product 1", new String[]{"url1", "url2"}, "Description 1", new String[]{"tag1", "tag2"}));
                productRepository.save(new Product("Product 2", new String[]{"url3", "url4"}, "Description 2", new String[]{"tag3", "tag4"}));
                productRepository.save(new Product("Product 3", new String[]{"url5", "url6"}, "Description 3", new String[]{"tag5", "tag6"}));
                productRepository.save(new Product("Product 4", new String[]{"url7", "url8"}, "Description 4", new String[]{"tag7", "tag8"}));
                productRepository.save(new Product("Product 5", new String[]{"url9", "url10"}, "Description 5", new String[]{"tag9", "tag10"}));
            }
        };
    }
}
