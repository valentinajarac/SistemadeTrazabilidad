package com.fruitsales.controller;

import com.fruitsales.dto.FruitShipmentResponseDTO;
import com.fruitsales.service.ExportService;
import com.fruitsales.service.FruitShipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/export")
@CrossOrigin(origins = "*")
public class ExportController {

    private final ExportService exportService;
    private final FruitShipmentService shipmentService;

    @Autowired
    public ExportController(ExportService exportService, FruitShipmentService shipmentService) {
        this.exportService = exportService;
        this.shipmentService = shipmentService;
    }

    @GetMapping("/excel/all")
    public ResponseEntity<byte[]> exportAllToExcel() throws IOException {
        List<FruitShipmentResponseDTO> shipments = shipmentService.getAllShipments();
        byte[] excelFile = exportService.exportToExcel(shipments);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=remisiones.xlsx")
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(excelFile);
    }

    @GetMapping("/pdf/all")
    public ResponseEntity<byte[]> exportAllToPdf() throws IOException {
        List<FruitShipmentResponseDTO> shipments = shipmentService.getAllShipments();
        byte[] pdfFile = exportService.exportToPdf(shipments);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=remisiones.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfFile);
    }

    @GetMapping("/excel/producer")
    public ResponseEntity<byte[]> exportProducerToExcel(Authentication authentication) throws IOException {
        String username = authentication.getName();
        List<FruitShipmentResponseDTO> shipments = shipmentService.getShipmentsByProducer(username);
        byte[] excelFile = exportService.exportToExcel(shipments);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=mis-remisiones.xlsx")
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(excelFile);
    }

    @GetMapping("/pdf/producer")
    public ResponseEntity<byte[]> exportProducerToPdf(Authentication authentication) throws IOException {
        String username = authentication.getName();
        List<FruitShipmentResponseDTO> shipments = shipmentService.getShipmentsByProducer(username);
        byte[] pdfFile = exportService.exportToPdf(shipments);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=mis-remisiones.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfFile);
    }
}