package com.fruitsales.dto;

import lombok.Data;

@Data
public class CropDTO {
    private Long id;
    private String name;
    private String variety;
    private String season;
    private String description;
}