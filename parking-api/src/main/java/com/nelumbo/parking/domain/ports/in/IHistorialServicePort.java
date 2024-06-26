package com.nelumbo.parking.domain.ports.in;

import com.nelumbo.parking.domain.model.HistorialModel;
import com.nelumbo.parking.domain.model.VehicleModel;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface IHistorialServicePort {
    Map<String, Long> registerVehicleEntry(VehicleModel vehicleModel, Long parkingId);
    List<HistorialModel> getAllVehiclesInParking(Integer page, Integer size, Long parkingId);
    Map<String, String> registerVehicleDeparture(String licensePlate, Long parkingId);
    List<Object[]> getTop10MostParkedVehicles();
    List<Object[]> getTop10MostParkedVehiclesByParking(Long parkingId);
    List<Object[]> getFirstTimeParkedVehiclesByParking(Long parkingId);
    List<HistorialModel> getVehicleByLicensePlate(String licensePlate);
    Map<String, BigDecimal> getCashIncomeByParking(Long parkingId);
}
