package com.fruitsales.service;

import com.fruitsales.dto.FruitShipmentDTO;
import com.fruitsales.dto.FruitShipmentResponseDTO;
import com.fruitsales.exception.CustomException;
import com.fruitsales.mapper.FruitShipmentMapper;
import com.fruitsales.model.FruitShipment;
import com.fruitsales.model.User;
import com.fruitsales.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class FruitShipmentService {

    private final FruitShipmentRepository shipmentRepository;
    private final UserRepository userRepository;
    private final ClientRepository clientRepository;
    private final FarmRepository farmRepository;
    private final CropRepository cropRepository;
    private final FruitShipmentMapper shipmentMapper;

    @Autowired
    public FruitShipmentService(FruitShipmentRepository shipmentRepository,
                               UserRepository userRepository,
                               ClientRepository clientRepository,
                               FarmRepository farmRepository,
                               CropRepository cropRepository,
                               FruitShipmentMapper shipmentMapper) {
        this.shipmentRepository = shipmentRepository;
        this.userRepository = userRepository;
        this.clientRepository = clientRepository;
        this.farmRepository = farmRepository;
        this.cropRepository = cropRepository;
        this.shipmentMapper = shipmentMapper;
    }

    // ... (previous methods remain the same)

    public List<FruitShipmentResponseDTO> getShipmentsByProducer(String username) {
        User producer = userRepository.findByUsername(username)
                .orElseThrow(() -> new CustomException("Productor no encontrado", HttpStatus.NOT_FOUND));
        
        return shipmentRepository.findByProducerId(producer.getId()).stream()
                .map(shipmentMapper::toDTO)
                .collect(Collectors.toList());
    }
}