package com.traceability.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class FarmDTO {
    @NotBlank(message = "El nombre de la finca es requerido")
    private String nombre;

    @NotNull(message = "Las hectáreas son requeridas")
    @Positive(message = "Las hectáreas deben ser un número positivo")
    private Double hectareas;

    @NotBlank(message = "El municipio es requerido")
    private String municipio;
}
