package com.coffeeshopsystem.coffeeshopsystem.controller;

import com.coffeeshopsystem.coffeeshopsystem.model.ServiceDelivery;
import com.coffeeshopsystem.coffeeshopsystem.service.ServiceDeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/service_delivery")
public class ServiceDeliveryController {

    @Autowired
    private ServiceDeliveryService serviceDeliveryService;

    @GetMapping("/{id}")
    public ResponseEntity<ServiceDelivery> getServiceDeliveryById(@PathVariable int id) {
        ServiceDelivery serviceDelivery = serviceDeliveryService.getById(id);
        if (serviceDelivery != null) {
            return ResponseEntity.ok(serviceDelivery);
        } else {
            return ResponseEntity.status(404).body(null);
        }
    }

    @GetMapping
    public ResponseEntity<List<ServiceDelivery>> getAllServiceDeliveries() {
        List<ServiceDelivery> serviceDeliveries = serviceDeliveryService.list();
        return ResponseEntity.ok(serviceDeliveries);
    }

    @PostMapping
    public ResponseEntity<String> createServiceDelivery(@RequestBody ServiceDelivery serviceDelivery) {
        serviceDeliveryService.save(serviceDelivery);
        return ResponseEntity.status(201).body("Service delivery created successfully.");
    }

    @PutMapping
    public ResponseEntity<String> updateServiceDelivery(@RequestBody ServiceDelivery serviceDelivery) {
        if (serviceDeliveryService.updateById(serviceDelivery)) {
            return ResponseEntity.ok("Service delivery updated successfully.");
        } else {
            return ResponseEntity.status(404).body("Service delivery not found.");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteServiceDelivery(@PathVariable int id) {
        if (serviceDeliveryService.removeById(id)) {
            return ResponseEntity.ok("Service delivery deleted successfully.");
        } else {
            return ResponseEntity.status(404).body("Service delivery not found.");
        }
    }
}