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

    public FruitShipmentResponseDTO createShipment(FruitShipmentDTO shipmentDTO) {
        FruitShipment shipment = new FruitShipment();

        shipment.setProducer(userRepository.findById(shipmentDTO.getProducerId())
                .orElseThrow(() -> new CustomException("Productor no encontrado", HttpStatus.NOT_FOUND)));

        shipment.setClient(clientRepository.findById(shipmentDTO.getClientId())
                .orElseThrow(() -> new CustomException("Cliente no encontrado", HttpStatus.NOT_FOUND)));

        shipment.setFarm(farmRepository.findById(shipmentDTO.getFarmId())
                .orElseThrow(() -> new CustomException("Finca no encontrada", HttpStatus.NOT_FOUND)));

        shipment.setCrop(cropRepository.findById(shipmentDTO.getCropId())
                .orElseThrow(() -> new CustomException("Cultivo no encontrado", HttpStatus.NOT_FOUND)));

        shipment.setAverageWeight(shipmentDTO.getAverageWeight());
        shipment.setBasketsSent(shipmentDTO.getBasketsSent());
        shipment.setShipmentDate(LocalDateTime.now());

        return shipmentMapper.toDTO(shipmentRepository.save(shipment));
    }

    public List<FruitShipmentResponseDTO> getAllShipments() {
        return shipmentRepository.findAll().stream()
                .map(shipmentMapper::toDTO)
                .collect(Collectors.toList());
    }

    public FruitShipmentResponseDTO getShipmentById(Long id) {
        FruitShipment shipment = shipmentRepository.findById(id)
                .orElseThrow(() -> new CustomException("Remisión no encontrada", HttpStatus.NOT_FOUND));
        return shipmentMapper.toDTO(shipment);
    }

    public List<FruitShipmentResponseDTO> getShipmentsByProducer(String username) {
        User producer = userRepository.findByUsername(username)
                .orElseThrow(() -> new CustomException("Productor no encontrado", HttpStatus.NOT_FOUND));

        return shipmentRepository.findByProducerId(producer.getId()).stream()
                .map(shipmentMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<FruitShipmentResponseDTO> getShipmentsByProducer(Long producerId) {
        if (!userRepository.existsById(producerId)) {
            throw new CustomException("Productor no encontrado", HttpStatus.NOT_FOUND);
        }

        return shipmentRepository.findByProducerId(producerId).stream()
                .map(shipmentMapper::toDTO)
                .collect(Collectors.toList());
    }

    public void deleteShipment(Long id) {
        if (!shipmentRepository.existsById(id)) {
            throw new CustomException("Remisión no encontrada", HttpStatus.NOT_FOUND);
        }
        shipmentRepository.deleteById(id);
    }
}