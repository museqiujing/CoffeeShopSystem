package com.coffeeshopsystem.coffeeshopsystem.repository;

import com.coffeeshopsystem.coffeeshopsystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}