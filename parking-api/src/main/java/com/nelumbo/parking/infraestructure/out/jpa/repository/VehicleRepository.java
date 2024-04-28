package com.nelumbo.parking.infraestructure.out.jpa.repository;

import com.nelumbo.parking.infraestructure.out.jpa.entity.VehicleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VehicleRepository extends JpaRepository<VehicleEntity, Long> {
    Optional<VehicleEntity> findByLicensePlate(String licensePlate);
}
