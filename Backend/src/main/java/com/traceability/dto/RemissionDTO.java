package com.traceability.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class RemissionDTO {
    @NotNull(message = "La fecha de despacho es requerida")
    private LocalDateTime fechaDespacho;

    @NotNull(message = "El cliente es requerido")
    private Long clienteId;

    @NotNull(message = "La finca es requerida")
    private Long fincaId;

    @NotNull(message = "El cultivo es requerido")
    private Long cultivoId;

    @NotNull(message = "El número de canastillas es requerido")
    @Positive(message = "El número de canastillas debe ser positivo")
    private Integer canastillasEnviadas;

    @NotNull(message = "Los kilos promedio por canastilla son requeridos")
    @Positive(message = "Los kilos promedio por canastilla deben ser positivos")
    private Double kilosPromedioPorCanastilla;
}
