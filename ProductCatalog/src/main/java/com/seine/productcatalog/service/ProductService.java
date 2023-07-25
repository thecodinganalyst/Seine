package com.seine.productcatalog.service;

import com.seine.productcatalog.model.Product;
import com.seine.productcatalog.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Service
public class ProductService {
    private final ProductRepository repository;

    public ProductService(ProductRepository repository){
        this.repository = repository;
    }

    public List<Product> list(){
        return repository.findAll();
    }

    public Product get(String id){
        return repository.findById(id).orElseThrow();
    }

    public Product create(Product product){
        return repository.save(product);
    }

    public Product edit(String id, Product product){
        Product savedProduct = repository.findById(id).orElseThrow();
        savedProduct.setName(product.getName());
        savedProduct.setDescription(product.getDescription());
        savedProduct.setImageUrls(product.getImageUrls());
        savedProduct.setTags(product.getTags());
        return repository.save(savedProduct);
    }

    public void delete(String id){
        if(repository.findById(id).isEmpty()) throw new NoSuchElementException();
        repository.deleteById(id);
    }

    public List<Product> searchByIds(List<String> idList){
        return repository.findAllByIdIn(idList);
    }

}
