package com.fruitsales.dto;

import lombok.Data;

@Data
public class FarmDTO {
    private Long id;
    private String name;
    private String location;
    private Double area;
    private String description;
}