package com.seine.productcatalog.service;

import com.seine.productcatalog.model.Product;
import com.seine.productcatalog.repository.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {
    @Mock
    private ProductRepository repository;

    @InjectMocks
    private ProductService service;

    @Test
    @DisplayName("Should return all products")
    void listShouldReturnAllProducts() {
        List<Product> expectedProducts = Arrays.asList(
                new Product("Product 1", new String[]{"url1", "url2"}, "Description 1", new String[]{"tag1", "tag2"}),
                new Product("Product 2", new String[]{"url3", "url4"}, "Description 2", new String[]{"tag3", "tag4"})
        );

        when(repository.findAll()).thenReturn(expectedProducts);

        List<Product> actualProducts = service.list();

        assertEquals(expectedProducts.size(), actualProducts.size());
        assertEquals(expectedProducts.get(0).getName(), actualProducts.get(0).getName());
        assertEquals(expectedProducts.get(1).getName(), actualProducts.get(1).getName());
        assertEquals(expectedProducts.get(0).getDescription(), actualProducts.get(0).getDescription());
        assertEquals(expectedProducts.get(1).getDescription(), actualProducts.get(1).getDescription());
        assertArrayEquals(expectedProducts.get(0).getImageUrls(), actualProducts.get(0).getImageUrls());
        assertArrayEquals(expectedProducts.get(1).getImageUrls(), actualProducts.get(1).getImageUrls());
        assertArrayEquals(expectedProducts.get(0).getTags(), actualProducts.get(0).getTags());
        assertArrayEquals(expectedProducts.get(1).getTags(), actualProducts.get(1).getTags());

        verify(repository, times(1)).findAll();
    }

}
