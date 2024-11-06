package com.traceability.controller;

import com.traceability.dto.AuthRequest;
import com.traceability.dto.AuthResponse;
import com.traceability.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody AuthRequest request) {
        log.debug("Login attempt for user: {}", request.getUsuario());
        AuthResponse response = authService.authenticate(request);
        log.debug("Login successful for user: {}", request.getUsuario());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/validate")
    public ResponseEntity<Void> validateToken() {
        return ResponseEntity.ok().build();
    }
}

