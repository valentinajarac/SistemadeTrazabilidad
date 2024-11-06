package com.traceability.dto;

import com.traceability.model.CropState;
import com.traceability.model.ProductType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class CropDTO {
    @NotNull(message = "El número de plantas es requerido")
    @Positive(message = "El número de plantas debe ser positivo")
    private Integer numeroPlants;
    
    @NotNull(message = "Las hectáreas son requeridas")
    @Positive(message = "Las hectáreas deben ser un número positivo")
    private Double hectareas;
    
    @NotNull(message = "El producto es requerido")
    private ProductType producto;
    
    @NotNull(message = "El estado es requerido")
    private CropState estado;
}