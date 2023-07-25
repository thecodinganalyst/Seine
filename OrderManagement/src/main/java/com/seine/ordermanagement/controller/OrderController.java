package com.seine.ordermanagement.controller;

import com.seine.ordermanagement.model.Order;
import com.seine.ordermanagement.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService service;

    public OrderController(OrderService service){
        this.service = service;
    }

    @PostMapping
    public Order createOrder(@RequestBody Order order){
         return service.createOrder(order);
    }

    @GetMapping
    public List<Order> listOrders(){
        return service.listOrders();
    }

    @GetMapping("/{id}")
    public Order getOrder(@PathVariable String id){
        try{
            return service.getOrder(id);
        }catch (NoSuchElementException ex){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public Order updateOrder(@PathVariable String id, @RequestBody Order order){
        try{
            return service.updateOrder(id, order);
        }catch (NoSuchElementException ex){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

}
