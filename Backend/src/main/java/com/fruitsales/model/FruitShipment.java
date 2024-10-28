package com.fruitsales.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "fruit_shipments")
public class FruitShipment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "producer_id", nullable = false)
    @NotNull(message = "El productor es obligatorio")
    private User producer;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    @NotNull(message = "El cliente es obligatorio")
    private Client client;

    @ManyToOne
    @JoinColumn(name = "farm_id", nullable = false)
    @NotNull(message = "La finca es obligatoria")
    private Farm farm;

    @ManyToOne
    @JoinColumn(name = "crop_id", nullable = false)
    @NotNull(message = "El cultivo es obligatorio")
    private Crop crop;

    @Min(value = 0, message = "El peso promedio debe ser mayor o igual a 0")
    private Double averageWeight;

    @Min(value = 1, message = "La cantidad de canastas debe ser al menos 1")
    private Integer basketsSent;

    private Double totalKilosSent;

    private LocalDateTime shipmentDate;

    @PrePersist
    @PreUpdate
    public void calculateTotalKilos() {
        if (averageWeight != null && basketsSent != null) {
            this.totalKilosSent = averageWeight * basketsSent;
        }
    }
}