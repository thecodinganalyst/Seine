package com.seine.ordermanagement.model;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;

@Document(collection = "Order")
@Data
public class Order {
    OrderStatus status;
    List<OrderItem> items;
    @CreatedDate
    LocalDate created;
    @LastModifiedDate
    LocalDate lastModified;
}
