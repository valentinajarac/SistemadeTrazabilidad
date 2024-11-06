package com.traceability.model;

import jakarta.persistence.*;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "crops")
public class Crop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "numero_plants", nullable = false)
    private Integer numeroPlants;

    @Column(nullable = false)
    private Double hectareas;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProductType producto;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CropState estado;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "productor_id", nullable = false)
    @JsonIgnoreProperties({
            "password", "authorities", "accountNonExpired",
            "accountNonLocked", "credentialsNonExpired",
            "enabled", "hibernateLazyInitializer"
    })
    private User productor;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}

