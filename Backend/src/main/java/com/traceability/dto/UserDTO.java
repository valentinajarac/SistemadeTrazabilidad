package com.traceability.dto;

import com.traceability.model.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserDTO {
    @NotNull(message = "La cédula es requerida")
    private Integer cedula;

    @NotBlank(message = "El nombre completo es requerido")
    private String nombreCompleto;

    @NotNull(message = "El código de trazabilidad es requerido")
    private Integer codigoTrazabilidad;

    @NotBlank(message = "El municipio es requerido")
    private String municipio;

    @NotBlank(message = "El teléfono es requerido")
    private String telefono;

    @NotBlank(message = "El usuario es requerido")
    private String usuario;

    private String password;

    @NotNull(message = "El rol es requerido")
    private Role role;
}