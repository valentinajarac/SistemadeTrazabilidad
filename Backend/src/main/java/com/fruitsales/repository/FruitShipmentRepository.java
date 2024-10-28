package com.fruitsales.repository;

import com.fruitsales.model.FruitShipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FruitShipmentRepository extends JpaRepository<FruitShipment, Long> {
    List<FruitShipment> findByProducerId(Long producerId);
    List<FruitShipment> findByClientId(Long clientId);
    List<FruitShipment> findByFarmId(Long farmId);
    List<FruitShipment> findByCropId(Long cropId);
}