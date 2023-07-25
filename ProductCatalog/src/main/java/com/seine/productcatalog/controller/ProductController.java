package com.seine.productcatalog.controller;

import com.seine.productcatalog.model.Product;
import com.seine.productcatalog.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService service;

    public ProductController(ProductService service){
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Product create(Product product){
        return service.create(product);
    }

    @GetMapping
    public List<Product> list(){
        return service.list();
    }

    @GetMapping("/{id}")
    public Product get(@PathVariable("id") String id){
        return service.get(id);
    }

    @PutMapping("/{productId}")
    public Product edit(@PathVariable("productId") String productId, @RequestBody Product product){
        return service.edit(productId, product);
    }

    @DeleteMapping("/{productId}")
    public void delete(@PathVariable("productId") String productId){
        service.delete(productId);
    }

}
