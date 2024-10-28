package com.fruitsales.controller;

import com.fruitsales.dto.FruitShipmentDTO;
import com.fruitsales.dto.FruitShipmentResponseDTO;
import com.fruitsales.service.FruitShipmentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shipments")
@CrossOrigin(origins = "*")
public class FruitShipmentController {

    private final FruitShipmentService shipmentService;

    @Autowired
    public FruitShipmentController(FruitShipmentService shipmentService) {
        this.shipmentService = shipmentService;
    }

    @PostMapping
    public ResponseEntity<FruitShipmentResponseDTO> createShipment(@Valid @RequestBody FruitShipmentDTO shipmentDTO) {
        return new ResponseEntity<>(shipmentService.createShipment(shipmentDTO), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<FruitShipmentResponseDTO>> getAllShipments() {
        return ResponseEntity.ok(shipmentService.getAllShipments());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FruitShipmentResponseDTO> getShipmentById(@PathVariable Long id) {
        return ResponseEntity.ok(shipmentService.getShipmentById(id));
    }

    @GetMapping("/producer/{producerId}")
    public ResponseEntity<List<FruitShipmentResponseDTO>> getShipmentsByProducer(@PathVariable Long producerId) {
        return ResponseEntity.ok(shipmentService.getShipmentsByProducer(producerId));
    }
}