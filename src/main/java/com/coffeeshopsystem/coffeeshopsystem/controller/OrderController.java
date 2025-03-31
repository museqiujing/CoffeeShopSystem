package com.coffeeshopsystem.coffeeshopsystem.controller;

import com.coffeeshopsystem.coffeeshopsystem.model.Order;
import com.coffeeshopsystem.coffeeshopsystem.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable int id) {
        Order order = orderService.getById(id);
        if (order != null) {
            return ResponseEntity.ok(order);
        } else {
            return ResponseEntity.status(404).body(null);
        }
    }

    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders = orderService.list();
        return ResponseEntity.ok(orders);
    }

    @PostMapping
    public ResponseEntity<String> createOrder(@RequestBody Order order) {
        orderService.save(order);
        return ResponseEntity.status(201).body("Order created successfully.");
    }

    @PutMapping
    public ResponseEntity<String> updateOrder(@RequestBody Order order) {
        if (orderService.updateById(order)) {
            return ResponseEntity.ok("Order updated successfully.");
        } else {
            return ResponseEntity.status(404).body("Order not found.");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOrder(@PathVariable int id) {
        if (orderService.removeById(id)) {
            return ResponseEntity.ok("Order deleted successfully.");
        } else {
            return ResponseEntity.status(404).body("Order not found.");
        }
    }
}