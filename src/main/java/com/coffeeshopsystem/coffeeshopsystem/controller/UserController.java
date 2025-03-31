package com.coffeeshopsystem.coffeeshopsystem.controller;

import com.coffeeshopsystem.coffeeshopsystem.model.User;
import com.coffeeshopsystem.coffeeshopsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable int id) {
        User user = userService.getById(id);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.list();
        return ResponseEntity.ok(users);
    }

    @PostMapping
    public ResponseEntity<String> insertUser(@RequestBody User user) {
        if (user.getUsername() == null || user.getPassword() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username and Password are required fields.");
        }
        userService.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body("User created successfully.");
    }

    @PutMapping
    public ResponseEntity<String> updateUser(@RequestBody User user) {
        if (userService.updateById(user)) {
            return ResponseEntity.ok("User updated successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable int id) {
        if (userService.removeById(id)) {
            return ResponseEntity.ok("User deleted successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        }
    }
}