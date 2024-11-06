package com.traceability.model;

import jakarta.persistence.*;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "remissions")
public class Remission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    @Column(name = "fecha_despacho", nullable = false)
    private LocalDateTime fechaDespacho;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "productor_id", nullable = false)
    private User productor;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Client cliente;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "finca_id", nullable = false)
    private Farm finca;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cultivo_id", nullable = false)
    private Crop cultivo;

    @Column(name = "canastillas_enviadas", nullable = false)
    private Integer canastillasEnviadas;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProductType producto;

    @Column(name = "kilos_promedio_canastilla", nullable = false)
    private Double kilosPromedioPorCanastilla;

    @Column(name = "total_kilos", nullable = false)
    private Double totalKilos;

    @PrePersist
    @PreUpdate
    public void calculateTotalKilos() {
        this.totalKilos = this.canastillasEnviadas * this.kilosPromedioPorCanastilla;
    }
}
