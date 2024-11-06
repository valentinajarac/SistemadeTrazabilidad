package com.traceability.dto;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;

@Data
public class AuthRequest {
    @NotBlank
    private String usuario;
    
    @NotBlank
    private String password;
}