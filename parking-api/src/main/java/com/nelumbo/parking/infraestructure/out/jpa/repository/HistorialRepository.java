package com.nelumbo.parking.infraestructure.out.jpa.repository;

import com.nelumbo.parking.infraestructure.out.jpa.entity.HistorialEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface HistorialRepository extends JpaRepository<HistorialEntity, Long> {
    List<HistorialEntity> findByParkingIdAndDepartureDateIsNull(Long parkingId);
    HistorialEntity findByVehicleIdAndDepartureDateIsNull(Long vehicleId);
    Page<HistorialEntity> findByParkingIdAndDepartureDateIsNull(Pageable pageable, Long parkingId);
    HistorialEntity findByVehicleIdAndParkingIdAndDepartureDateIsNull(Long vehicleId, Long parkingId);


    //Top 10 vehiculos mas parqueados en los diferentes parqueaderos
    @Query("SELECT h.vehicle.id AS id, h.vehicle.licensePlate AS licensePlate, COUNT(h.vehicle.id) AS count " +
            "FROM HistorialEntity h " +
            "GROUP BY id, licensePlate " +
            "ORDER BY count DESC " +
            "LIMIT 10")
    List<Object[]> getTop10MostParkedVehicles(); //Admin
    @Query("SELECT h.vehicle.id AS id, h.vehicle.licensePlate AS licensePlate, COUNT(h.vehicle.id) AS count " +
            "FROM HistorialEntity h JOIN h.parking p JOIN p.user u " +
            "WHERE u.id =:socioId " +
            "GROUP BY id, licensePlate " +
            "ORDER BY count DESC " +
            "LIMIT 10")
    List<Object[]> getTop10MostParkedVehiclesInSocioParkings(Long socioId); //Socio


    //Top 10 vehiculos mas parqueados en un parquadero
    @Query("SELECT h.vehicle.id AS id, h.vehicle.licensePlate AS licensePlate, COUNT(h.vehicle.id) AS count " +
            "FROM HistorialEntity h " +
            "WHERE h.parking.id =:parkingId " +
            "GROUP BY id, licensePlate " +
            "ORDER BY count DESC " +
            "LIMIT 10")
    List<Object[]> getTop10MostParkedVehiclesByParking(Long parkingId); //Admin y Socio


    //Obtener los vehiculos parqueados por primera vez en un parqueadero
    @Query("SELECT h.vehicle.id AS id, h.vehicle.licensePlate AS licensePlate " +
            "FROM HistorialEntity h " +
            "WHERE h.parking.id =:parkingId " +
            "GROUP BY id, licensePlate " +
            "HAVING COUNT(h.vehicle.id) = 1")
    List<Object[]> getFirstTimeParkedVehiclesByParking(Long parkingId); //Admin y Socio


    //Buscar vehiculos parqueados por coincidencia de placa
    List<HistorialEntity> findByVehicleLicensePlateContainingIgnoreCaseAndDepartureDateIsNull(String licensePlate); //Admin
    @Query("SELECT h " +
            "FROM HistorialEntity h JOIN h.parking p JOIN p.user u " +
            "WHERE u.id =:socioId " +
            "AND h.vehicle.licensePlate ILIKE %:licensePlate% " +
            "AND h.departureDate IS NULL")
    List<HistorialEntity> findByVehicleLicensePlateContainingIgnoreCaseAndDepartureDateIsNullInSocioParkings(Long socioId, String licensePlate); //Socio
}
