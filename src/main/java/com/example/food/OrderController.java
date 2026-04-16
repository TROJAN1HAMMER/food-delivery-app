package com.example.food;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final ConcurrentHashMap<String, Order> orderDatabase = new ConcurrentHashMap<>();

    @PostMapping
    public ResponseEntity<String> placeOrder(@RequestBody Order order) {
        if (orderDatabase.containsKey(order.orderId())) {
            return ResponseEntity.status(409).body("Order ID " + order.orderId() + " already exists.");
        }
        
        Order newOrder = new Order(order.orderId(), order.item(), "PLACED");
        orderDatabase.put(order.orderId(), newOrder);
        return ResponseEntity.status(201).body("Order processed successfully.");
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> trackOrder(@PathVariable String id) {
        Order order = orderDatabase.get(id);
        return order != null ? ResponseEntity.ok(order) : ResponseEntity.notFound().build();
    }
}