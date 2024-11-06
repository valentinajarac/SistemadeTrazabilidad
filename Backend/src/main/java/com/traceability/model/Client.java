package com.traceability.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Entity
@Table(name = "clients")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false)
    private Long nit;
    
    @NotBlank
    @Column(nullable = false)
    private String nombre;
    
    @Pattern(regexp = "\\d{4}")
    @Column(nullable = false)
    private String floid;
    
    @NotBlank
    @Column(nullable = false)
    private String direccion;
    
    @NotBlank
    @Column(nullable = false)
    private String telefono;
    
    @Email
    @Column(unique = true, nullable = false)
    private String email;
}