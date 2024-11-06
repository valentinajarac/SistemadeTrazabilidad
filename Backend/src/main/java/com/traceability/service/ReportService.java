package com.traceability.service;

import com.traceability.dto.MonthlyReport;
import com.traceability.exception.ResourceNotFoundException;
import com.traceability.model.Role;
import com.traceability.model.User;
import com.traceability.repository.RemissionRepository;
import com.traceability.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;

import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReportService {

    private final RemissionRepository remissionRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<MonthlyReport> getMonthlyReport(UserDetails userDetails) {
        log.debug("Generating monthly report for user: {}", userDetails.getUsername());

        User user = getUserFromUserDetails(userDetails);
        LocalDateTime startDate = LocalDateTime.now().withDayOfYear(1).withHour(0).withMinute(0).withSecond(0);
        LocalDateTime endDate = LocalDateTime.now();

        Long productorId = user.getRole() == Role.ADMIN ? null : user.getId();

        List<MonthlyReport> reports = remissionRepository.getMonthlyReport(productorId, startDate, endDate);
        log.debug("Generated report with {} entries", reports.size());

        return reports;
    }

    public Resource generateExcelReport(UserDetails userDetails) throws Exception {
        log.debug("Generating Excel report for user: {}", userDetails.getUsername());
        List<MonthlyReport> reports = getMonthlyReport(userDetails);

        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Reporte Mensual");

            // Create header row
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("Mes");
            headerRow.createCell(1).setCellValue("Producto");
            headerRow.createCell(2).setCellValue("Total Kilos");

            // Fill data
            int rowNum = 1;
            for (MonthlyReport report : reports) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(report.getId().getMonth());
                row.createCell(1).setCellValue(report.getId().getProducto().toString());
                row.createCell(2).setCellValue(report.getTotalKilos());
            }

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            return new ByteArrayResource(outputStream.toByteArray());
        }
    }

    public Resource generatePdfReport(UserDetails userDetails) throws Exception {
        log.debug("Generating PDF report for user: {}", userDetails.getUsername());
        List<MonthlyReport> reports = getMonthlyReport(userDetails);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        PdfWriter writer = new PdfWriter(outputStream);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        document.add(new Paragraph("Reporte de Producción Mensual"));
        document.add(new Paragraph("Fecha de generación: " +
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"))));

        Table table = new Table(3);
        table.addCell("Mes");
        table.addCell("Producto");
        table.addCell("Total Kilos");

        for (MonthlyReport report : reports) {
            table.addCell(String.valueOf(report.getId().getMonth()));
            table.addCell(report.getId().getProducto().toString());
            table.addCell(String.format("%.2f", report.getTotalKilos()));
        }

        document.add(table);
        document.close();

        return new ByteArrayResource(outputStream.toByteArray());
    }

    private User getUserFromUserDetails(UserDetails userDetails) {
        return userRepository.findByUsuario(userDetails.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
    }
}
