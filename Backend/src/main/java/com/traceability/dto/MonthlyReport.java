package com.traceability.dto;

import com.traceability.model.ProductType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MonthlyReport {
    private ReportId id;
    private Double totalKilos;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ReportId {
        private Integer month;
        private ProductType producto;
    }
}
