package com.coffeeshopsystem.coffeeshopsystem.controller;

import com.coffeeshopsystem.coffeeshopsystem.model.Menu;
import com.coffeeshopsystem.coffeeshopsystem.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/menus")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @GetMapping("/{id}")
    public ResponseEntity<Menu> getMenuById(@PathVariable int id) {
        Menu menu = menuService.getById(id);
        if (menu != null) {
            return ResponseEntity.ok(menu);
        } else {
            return ResponseEntity.status(404).body(null);
        }
    }

    @GetMapping
    public ResponseEntity<List<Menu>> getAllMenus() {
        List<Menu> menus = menuService.list();
        return ResponseEntity.ok(menus);
    }

    @PostMapping
    public ResponseEntity<String> createMenu(@RequestBody Menu menu) {
        menuService.save(menu);
        return ResponseEntity.status(201).body("Menu created successfully.");
    }

    @PutMapping
    public ResponseEntity<String> updateMenu(@RequestBody Menu menu) {
        if (menuService.updateById(menu)) {
            return ResponseEntity.ok("Menu updated successfully.");
        } else {
            return ResponseEntity.status(404).body("Menu not found.");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMenu(@PathVariable int id) {
        if (menuService.removeById(id)) {
            return ResponseEntity.ok("Menu deleted successfully.");
        } else {
            return ResponseEntity.status(404).body("Menu not found.");
        }
    }
}