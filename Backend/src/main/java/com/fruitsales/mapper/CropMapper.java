package com.fruitsales.mapper;

import com.fruitsales.dto.CropDTO;
import com.fruitsales.model.Crop;
import org.springframework.stereotype.Component;

@Component
public class CropMapper {
    
    public CropDTO toDTO(Crop crop) {
        CropDTO dto = new CropDTO();
        dto.setId(crop.getId());
        dto.setName(crop.getName());
        dto.setVariety(crop.getVariety());
        dto.setSeason(crop.getSeason());
        dto.setDescription(crop.getDescription());
        return dto;
    }

    public Crop toEntity(CropDTO dto) {
        Crop crop = new Crop();
        crop.setId(dto.getId());
        crop.setName(dto.getName());
        crop.setVariety(dto.getVariety());
        crop.setSeason(dto.getSeason());
        crop.setDescription(dto.getDescription());
        return crop;
    }
}