package com.nelumbo.parking.infraestructure.out.jpa.repository;

import com.nelumbo.parking.infraestructure.out.jpa.entity.HistorialEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HistorialRepository extends JpaRepository<HistorialEntity, Long> {
    List<HistorialEntity> findByParkingIdAndDepartureDateIsNull(Long parkingId);
    HistorialEntity findByVehicleIdAndDepartureDateIsNull(Long vehicleId);
}
