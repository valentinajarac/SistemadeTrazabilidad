package com.fruitsales.mapper;

import com.fruitsales.dto.FarmDTO;
import com.fruitsales.model.Farm;
import org.springframework.stereotype.Component;

@Component
public class FarmMapper {
    
    public FarmDTO toDTO(Farm farm) {
        FarmDTO dto = new FarmDTO();
        dto.setId(farm.getId());
        dto.setName(farm.getName());
        dto.setLocation(farm.getLocation());
        dto.setArea(farm.getArea());
        dto.setDescription(farm.getDescription());
        return dto;
    }

    public Farm toEntity(FarmDTO dto) {
        Farm farm = new Farm();
        farm.setId(dto.getId());
        farm.setName(dto.getName());
        farm.setLocation(dto.getLocation());
        farm.setArea(dto.getArea());
        farm.setDescription(dto.getDescription());
        return farm;
    }
}