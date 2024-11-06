package com.traceability.repository;

import com.traceability.model.Farm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface FarmRepository extends JpaRepository<Farm, Long> {
    @Query("SELECT f FROM Farm f WHERE f.productor.id = :productorId")
    List<Farm> findByProductorId(@Param("productorId") Long productorId);

    @Query("SELECT f FROM Farm f LEFT JOIN FETCH f.productor WHERE f.id = :id")
    Optional<Farm> findByIdWithProductor(@Param("id") Long id);

    @Query("SELECT CASE WHEN COUNT(f) > 0 THEN true ELSE false END FROM Farm f WHERE f.id = :id AND f.productor.id = :productorId")
    boolean existsByIdAndProductorId(@Param("id") Long id, @Param("productorId") Long productorId);
}
