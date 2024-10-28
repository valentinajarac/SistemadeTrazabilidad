package com.fruitsales.dto;

import lombok.Data;

@Data
public class UserResponseDTO {
    private Long id;
    private String cedula;
    private String nombreCompleto;
    private String codigoTrazabilidad;
    private String username;
}