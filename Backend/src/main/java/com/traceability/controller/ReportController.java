package com.traceability.controller;

import com.traceability.dto.MonthlyReport;
import com.traceability.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/monthly")
    public ResponseEntity<List<MonthlyReport>> getMonthlyReport(
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(reportService.getMonthlyReport(userDetails));
    }

    @GetMapping("/download/excel")
    public ResponseEntity<Resource> downloadExcelReport(
            @AuthenticationPrincipal UserDetails userDetails) {
        try {
            Resource file = reportService.generateExcelReport(userDetails);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=report.xlsx")
                    .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                    .body(file);
        } catch (Exception e) {
            throw new RuntimeException("Error generating Excel report", e);
        }
    }

    @GetMapping("/download/pdf")
    public ResponseEntity<Resource> downloadPdfReport(
            @AuthenticationPrincipal UserDetails userDetails) {
        try {
            Resource file = reportService.generatePdfReport(userDetails);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=report.pdf")
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(file);
        } catch (Exception e) {
            throw new RuntimeException("Error generating PDF report", e);
        }
    }
}