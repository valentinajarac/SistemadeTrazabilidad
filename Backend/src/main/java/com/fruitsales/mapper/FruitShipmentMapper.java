package com.fruitsales.mapper;

import com.fruitsales.dto.FruitShipmentResponseDTO;
import com.fruitsales.model.FruitShipment;
import org.springframework.stereotype.Component;

@Component
public class FruitShipmentMapper {
    
    private final UserMapper userMapper;
    private final ClientMapper clientMapper;
    private final FarmMapper farmMapper;
    private final CropMapper cropMapper;

    public FruitShipmentMapper(UserMapper userMapper, 
                              ClientMapper clientMapper,
                              FarmMapper farmMapper,
                              CropMapper cropMapper) {
        this.userMapper = userMapper;
        this.clientMapper = clientMapper;
        this.farmMapper = farmMapper;
        this.cropMapper = cropMapper;
    }

    public FruitShipmentResponseDTO toDTO(FruitShipment shipment) {
        FruitShipmentResponseDTO dto = new FruitShipmentResponseDTO();
        dto.setId(shipment.getId());
        dto.setProducer(userMapper.toDTO(shipment.getProducer()));
        dto.setClient(clientMapper.toDTO(shipment.getClient()));
        dto.setFarm(farmMapper.toDTO(shipment.getFarm()));
        dto.setCrop(cropMapper.toDTO(shipment.getCrop()));
        dto.setAverageWeight(shipment.getAverageWeight());
        dto.setBasketsSent(shipment.getBasketsSent());
        dto.setTotalKilosSent(shipment.getTotalKilosSent());
        dto.setShipmentDate(shipment.getShipmentDate());
        return dto;
    }
}