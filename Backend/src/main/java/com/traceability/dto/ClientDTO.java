package com.traceability.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class ClientDTO {
    private Long nit;
    
    @NotBlank(message = "El nombre es requerido")
    private String nombre;
    
    @Pattern(regexp = "\\d{4}", message = "El FLOID debe ser un número de 4 dígitos")
    private String floid;
    
    @NotBlank(message = "La dirección es requerida")
    private String direccion;
    
    @NotBlank(message = "El teléfono es requerido")
    private String telefono;
    
    @Email(message = "El email debe ser válido")
    private String email;
}