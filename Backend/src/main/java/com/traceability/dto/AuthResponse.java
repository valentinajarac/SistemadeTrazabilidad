package com.traceability.dto;

import com.traceability.model.Role;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthResponse {
    private String token;
    private UserDTO user;

    @Data
    @Builder
    public static class UserDTO {
        private String id;
        private String nombreCompleto;
        private Role role;
        private String usuario;
        private String municipio;
        private Integer codigoTrazabilidad;
    }
}
