package com.nelumbo.parking.domain.ports.in;

import com.nelumbo.parking.domain.model.HistorialModel;
import com.nelumbo.parking.domain.model.VehicleModel;

import java.util.List;
import java.util.Map;

public interface IHistorialServicePort {
    Map<String, Long> registerVehicleEntry(VehicleModel vehicleModel, Long parkingId);
    List<HistorialModel> getAllVehiclesInParking(Integer page, Integer size, Long parkingId);
}
