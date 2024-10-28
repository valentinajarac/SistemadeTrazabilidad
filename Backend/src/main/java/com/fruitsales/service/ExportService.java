package com.fruitsales.service;

import com.fruitsales.dto.FruitShipmentResponseDTO;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class ExportService {

    public byte[] exportToExcel(List<FruitShipmentResponseDTO> shipments) throws IOException {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Remisiones");
            
            // Create header row
            Row headerRow = sheet.createRow(0);
            String[] columns = {"ID", "Productor", "Cliente", "Finca", "Cultivo", "Peso Promedio", 
                              "Canastas Enviadas", "Total Kilos", "Fecha de Envío"};
            
            for (int i = 0; i < columns.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columns[i]);
            }

            // Fill data rows
            int rowNum = 1;
            for (FruitShipmentResponseDTO shipment : shipments) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(shipment.getId());
                row.createCell(1).setCellValue(shipment.getProducer().getNombreCompleto());
                row.createCell(2).setCellValue(shipment.getClient().getName());
                row.createCell(3).setCellValue(shipment.getFarm().getName());
                row.createCell(4).setCellValue(shipment.getCrop().getName());
                row.createCell(5).setCellValue(shipment.getAverageWeight());
                row.createCell(6).setCellValue(shipment.getBasketsSent());
                row.createCell(7).setCellValue(shipment.getTotalKilosSent());
                row.createCell(8).setCellValue(shipment.getShipmentDate().toString());
            }

            // Autosize columns
            for (int i = 0; i < columns.length; i++) {
                sheet.autoSizeColumn(i);
            }

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            return outputStream.toByteArray();
        }
    }

    public byte[] exportToPdf(List<FruitShipmentResponseDTO> shipments) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(outputStream);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        document.add(new Paragraph("Reporte de Remisiones"));
        document.add(new Paragraph(" ")); // Spacing

        Table table = new Table(9);
        
        // Add headers
        table.addCell("ID");
        table.addCell("Productor");
        table.addCell("Cliente");
        table.addCell("Finca");
        table.addCell("Cultivo");
        table.addCell("Peso Promedio");
        table.addCell("Canastas");
        table.addCell("Total Kilos");
        table.addCell("Fecha");

        // Add data
        for (FruitShipmentResponseDTO shipment : shipments) {
            table.addCell(String.valueOf(shipment.getId()));
            table.addCell(shipment.getProducer().getNombreCompleto());
            table.addCell(shipment.getClient().getName());
            table.addCell(shipment.getFarm().getName());
            table.addCell(shipment.getCrop().getName());
            table.addCell(String.valueOf(shipment.getAverageWeight()));
            table.addCell(String.valueOf(shipment.getBasketsSent()));
            table.addCell(String.valueOf(shipment.getTotalKilosSent()));
            table.addCell(shipment.getShipmentDate().toString());
        }

        document.add(table);
        document.close();

        return outputStream.toByteArray();
    }
}