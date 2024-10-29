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

            // Create cell style for headers
            CellStyle headerStyle = workbook.createCellStyle();
            headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerStyle.setFont(headerFont);

            for (int i = 0; i < columns.length; i++) {
                org.apache.poi.ss.usermodel.Cell cell = headerRow.createCell(i);
                cell.setCellValue(columns[i]);
                cell.setCellStyle(headerStyle);
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

        // Add title
        Paragraph title = new Paragraph("Reporte de Remisiones")
                .setFontSize(16)
                .setBold();
        document.add(title);
        document.add(new Paragraph("\n")); // Add spacing

        // Create table
        Table table = new Table(9).useAllAvailableWidth();

        // Add headers
        String[] headers = {"ID", "Productor", "Cliente", "Finca", "Cultivo",
                "Peso Promedio", "Canastas", "Total Kilos", "Fecha"};

        for (String header : headers) {
            table.addCell(new Cell().add(new Paragraph(header)).setBold());
        }

        // Add data
        for (FruitShipmentResponseDTO shipment : shipments) {
            table.addCell(new Cell().add(new Paragraph(String.valueOf(shipment.getId()))));
            table.addCell(new Cell().add(new Paragraph(shipment.getProducer().getNombreCompleto())));
            table.addCell(new Cell().add(new Paragraph(shipment.getClient().getName())));
            table.addCell(new Cell().add(new Paragraph(shipment.getFarm().getName())));
            table.addCell(new Cell().add(new Paragraph(shipment.getCrop().getName())));
            table.addCell(new Cell().add(new Paragraph(String.valueOf(shipment.getAverageWeight()))));
            table.addCell(new Cell().add(new Paragraph(String.valueOf(shipment.getBasketsSent()))));
            table.addCell(new Cell().add(new Paragraph(String.valueOf(shipment.getTotalKilosSent()))));
            table.addCell(new Cell().add(new Paragraph(shipment.getShipmentDate().toString())));
        }

        document.add(table);
        document.close();

        return outputStream.toByteArray();
    }
}