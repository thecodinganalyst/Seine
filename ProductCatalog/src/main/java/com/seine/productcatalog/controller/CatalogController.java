package com.seine.productcatalog.controller;

import com.seine.productcatalog.model.Product;
import com.seine.productcatalog.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@Slf4j
public class CatalogController {
    private final ProductService service;

    public CatalogController(ProductService service){
        this.service = service;
    }

    @QueryMapping
    public List<Product> searchByIds(@Argument List<String> ids){
        log.info("graphql request for searchByIds - {}", ids);
        return this.service.searchByIds(ids);
    }
}
