package com.fruitsales.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class FruitShipmentResponseDTO {
    private Long id;
    private UserResponseDTO producer;
    private ClientDTO client;
    private FarmDTO farm;
    private CropDTO crop;
    private Double averageWeight;
    private Integer basketsSent;
    private Double totalKilosSent;
    private LocalDateTime shipmentDate;
}