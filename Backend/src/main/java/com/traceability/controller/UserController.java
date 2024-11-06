package com.traceability.controller;

import com.traceability.dto.UserDTO;
import com.traceability.model.User;
import com.traceability.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> createUser(@Valid @RequestBody UserDTO userDTO) {
        log.debug("Creating new user with username: {}", userDTO.getUsuario());
        User createdUser = userService.createUser(userDTO);
        log.debug("User created successfully with ID: {}", createdUser.getId());
        return ResponseEntity.ok(createdUser);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> updateUser(@PathVariable String id, @Valid @RequestBody UserDTO userDTO) {
        log.debug("Updating user with ID: {}", id);
        User updatedUser = userService.updateUser(id, userDTO);
        log.debug("User updated successfully");
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteUser(@PathVariable String id) {
        log.debug("Deleting user with ID: {}", id);
        userService.deleteUser(id);
        log.debug("User deleted successfully");
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<User>> getAllUsers() {
        log.debug("Fetching all users");
        return ResponseEntity.ok(userService.getAllUsers());
    }
}