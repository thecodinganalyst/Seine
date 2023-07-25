package com.seine.ordermanagement.service;

import com.seine.ordermanagement.model.Order;
import com.seine.ordermanagement.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

import static com.seine.ordermanagement.model.OrderStatus.Created;

@Service
public class OrderService {
    private final OrderRepository repository;

    public OrderService(OrderRepository repository){
        this.repository = repository;
    }

    public Order createOrder(Order order){
        order.setStatus(Created);
        return this.repository.save(order);
    }

    public List<Order> listOrders(){
        return this.repository.findAll();
    }

    public Order getOrder(String id){
        return this.repository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    public Order updateOrder(String id, Order order){
        Order savedOrder = repository.findById(id).orElseThrow(NoSuchElementException::new);
        savedOrder.setStatus(order.getStatus());
        savedOrder.setItems(order.getItems());
        return repository.save(savedOrder);
    }

    public void deleteOrder(String id){
        if(!repository.existsById(id)) throw new NoSuchElementException();
        repository.deleteById(id);
    }

}
