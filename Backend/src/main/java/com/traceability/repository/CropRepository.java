package com.traceability.repository;

import com.traceability.model.Crop;
import com.traceability.model.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CropRepository extends JpaRepository<Crop, Long> {
    @Query("SELECT c FROM Crop c WHERE c.productor.id = :productorId")
    List<Crop> findByProductorId(@Param("productorId") Long productorId);

    @Query("SELECT c FROM Crop c WHERE c.productor.id = :productorId AND c.producto = :producto")
    List<Crop> findByProductorIdAndProducto(@Param("productorId") Long productorId, @Param("producto") ProductType producto);
}
