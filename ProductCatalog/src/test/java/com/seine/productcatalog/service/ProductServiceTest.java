package com.seine.productcatalog.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.seine.productcatalog.model.Product;
import com.seine.productcatalog.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository repository;

    @InjectMocks
    private ProductService productService;

    @Test
    void shouldListAllProducts() {
        // Arrange
        Product product = new Product("Test", new String[] {"url1", "url2"}, "Description", new String[] {"tag1", "tag2"});
        when(repository.findAll()).thenReturn(List.of(product));

        // Act
        List<Product> products = productService.list();

        // Assert
        assertEquals(1, products.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    void shouldReturnProductById() {
        // Arrange
        String id = "1";
        Product product = new Product("Test", new String[] {"url1", "url2"}, "Description", new String[] {"tag1", "tag2"});
        when(repository.findById(id)).thenReturn(Optional.of(product));

        // Act
        Product foundProduct = productService.get(id);

        // Assert
        assertEquals(product, foundProduct);
        verify(repository, times(1)).findById(id);
    }

    @Test
    void shouldThrowExceptionWhenProductNotFound() {
        // Arrange
        String id = "testId";
        when(repository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NoSuchElementException.class, () -> productService.get(id));
        verify(repository, times(1)).findById(id);
    }

    @Test
    void shouldCreateProduct() {
        // Arrange
        Product product = new Product("Test", new String[] {"url1", "url2"}, "Description", new String[] {"tag1", "tag2"});
        when(repository.save(any(Product.class))).thenReturn(product);

        // Act
        Product savedProduct = productService.create(product);

        // Assert
        assertEquals(product, savedProduct);
        verify(repository, times(1)).save(product);
    }

    @Test
    void shouldEditProduct() {
        // Arrange
        String id = "1";
        Product oldProduct = new Product("Old Test", new String[] {"url1", "url2"}, "Old Description", new String[] {"tag1", "tag2"});
        Product newProduct = new Product("New Test", new String[] {"url1", "url2"}, "New Description", new String[] {"tag1", "tag2"});
        when(repository.findById(id)).thenReturn(Optional.of(oldProduct));
        when(repository.save(any(Product.class))).thenReturn(newProduct);

        // Act
        Product savedProduct = productService.edit(id, newProduct);

        // Assert
        assertEquals(newProduct, savedProduct);
        verify(repository, times(1)).findById(id);
        verify(repository, times(1)).save(oldProduct);
    }

    @Test
    void shouldDeleteProduct() {
        // Arrange
        String id = "1";
        Product product = new Product("Test", new String[] {"url1", "url2"}, "Description", new String[] {"tag1", "tag2"});
        when(repository.findById(id)).thenReturn(Optional.of(product));


        // Act
        productService.delete(id);

        // Assert
        verify(repository, times(1)).deleteById(id);
    }

    @Test
    void shouldListAllProductsWithId() {
        // Arrange
        Product product1 = new Product("Test 1", new String[] {"url1", "url2"}, "Description 1", new String[] {"tag1", "tag2"});
        Product product2 = new Product("Test 2", new String[] {"url1", "url2"}, "Description 2", new String[] {"tag1", "tag2"});
        when(repository.findAllByIdIn(List.of("1", "2"))).thenReturn(List.of(product1, product2));

        // Act
        List<Product> products = productService.searchByIds(List.of("1", "2"));

        // Assert
        assertEquals(2, products.size());
        verify(repository, times(1)).findAllByIdIn(any());
    }

}
