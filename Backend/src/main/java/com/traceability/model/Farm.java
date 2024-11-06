package com.traceability.model;

import jakarta.persistence.*;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonProperty;

@Data
@Entity
@Table(name = "farms")
public class Farm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty("nombreFinca")
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(nullable = false)
    private Double hectareas;

    @Column(nullable = false)
    private String municipio;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "productor_id", nullable = false)
    private User productor;

    @JsonProperty("productorId")
    public Long getProductorId() {
        return productor != null ? productor.getId() : null;
    }
}
