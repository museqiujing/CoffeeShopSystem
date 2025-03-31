package com.coffeeshopsystem.coffeeshopsystem.controller;

import com.coffeeshopsystem.coffeeshopsystem.model.Material;
import com.coffeeshopsystem.coffeeshopsystem.service.MaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/materials")
public class MaterialController {

    @Autowired
    private MaterialService materialService;

    @GetMapping("/{id}")
    public ResponseEntity<Material> getMaterialById(@PathVariable int id) {
        Material material = materialService.getById(id);
        if (material != null) {
            return ResponseEntity.ok(material);
        } else {
            return ResponseEntity.status(404).body(null);
        }
    }

    @GetMapping
    public ResponseEntity<List<Material>> getAllMaterials() {
        List<Material> materials = materialService.list();
        return ResponseEntity.ok(materials);
    }

    @PostMapping
    public ResponseEntity<String> createMaterial(@RequestBody Material material) {
        materialService.save(material);
        return ResponseEntity.status(201).body("Material created successfully.");
    }

    @PutMapping
    public ResponseEntity<String> updateMaterial(@RequestBody Material material) {
        if (materialService.updateById(material)) {
            return ResponseEntity.ok("Material updated successfully.");
        } else {
            return ResponseEntity.status(404).body("Material not found.");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMaterial(@PathVariable int id) {
        if (materialService.removeById(id)) {
            return ResponseEntity.ok("Material deleted successfully.");
        } else {
            return ResponseEntity.status(404).body("Material not found.");
        }
    }
}