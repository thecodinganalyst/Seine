package com.seine.productcatalog;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.junit.jupiter.Testcontainers;
@Testcontainers
@ContextConfiguration(classes = MongoDBTestContainerConfig.class)
class ProductCatalogApplicationTests {
    @Test
    void contextLoads() {
    }

}
