package com.traceability.repository;

import com.traceability.model.Remission;
import com.traceability.dto.MonthlyReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDateTime;
import java.util.List;

public interface RemissionRepository extends JpaRepository<Remission, Long> {
    List<Remission> findByProductorId(Long productorId);

    @Query("SELECT new com.traceability.dto.MonthlyReport(" +
            "new com.traceability.dto.MonthlyReport$ReportId(" +
            "FUNCTION('MONTH', r.fechaDespacho), r.producto), " +
            "SUM(r.totalKilos)) " +
            "FROM Remission r " +
            "WHERE (:productorId IS NULL OR r.productor.id = :productorId) " +
            "AND r.fechaDespacho BETWEEN :startDate AND :endDate " +
            "GROUP BY FUNCTION('MONTH', r.fechaDespacho), r.producto " +
            "ORDER BY FUNCTION('MONTH', r.fechaDespacho)")
    List<MonthlyReport> getMonthlyReport(
            @Param("productorId") Long productorId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );
}

