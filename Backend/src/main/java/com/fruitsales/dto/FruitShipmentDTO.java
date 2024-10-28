package com.fruitsales.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class FruitShipmentDTO {
    @NotNull(message = "El ID del productor es obligatorio")
    private Long producerId;

    @NotNull(message = "El ID del cliente es obligatorio")
    private Long clientId;

    @NotNull(message = "El ID de la finca es obligatorio")
    private Long farmId;

    @NotNull(message = "El ID del cultivo es obligatorio")
    private Long cropId;

    @Min(value = 0, message = "El peso promedio debe ser mayor o igual a 0")
    private Double averageWeight;

    @Min(value = 1, message = "La cantidad de canastas debe ser al menos 1")
    private Integer basketsSent;
}