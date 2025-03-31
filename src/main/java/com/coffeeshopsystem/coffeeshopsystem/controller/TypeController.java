package com.coffeeshopsystem.coffeeshopsystem.controller;

import com.coffeeshopsystem.coffeeshopsystem.model.Type;
import com.coffeeshopsystem.coffeeshopsystem.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/types")
public class TypeController {

    @Autowired
    private TypeService typeService;

    @GetMapping("/{id}")
    public ResponseEntity<Type> getTypeById(@PathVariable int id) {
        Type type = typeService.getById(id);
        if (type != null) {
            return ResponseEntity.ok(type);
        } else {
            return ResponseEntity.status(404).body(null);
        }
    }

    @GetMapping
    public ResponseEntity<List<Type>> getAllTypes() {
        List<Type> types = typeService.list();
        return ResponseEntity.ok(types);
    }

    @PostMapping
    public ResponseEntity<String> createType(@RequestBody Type type) {
        typeService.save(type);
        return ResponseEntity.status(201).body("Type created successfully.");
    }

    @PutMapping
    public ResponseEntity<String> updateType(@RequestBody Type type) {
        if (typeService.updateById(type)) {
            return ResponseEntity.ok("Type updated successfully.");
        } else {
            return ResponseEntity.status(404).body("Type not found.");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteType(@PathVariable int id) {
        if (typeService.removeById(id)) {
            return ResponseEntity.ok("Type deleted successfully.");
        } else {
            return ResponseEntity.status(404).body("Type not found.");
        }
    }
}