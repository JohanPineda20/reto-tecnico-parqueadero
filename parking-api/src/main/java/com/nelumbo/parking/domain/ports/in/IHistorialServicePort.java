package com.nelumbo.parking.domain.ports.in;

import com.nelumbo.parking.domain.model.VehicleModel;

import java.util.Map;

public interface IHistorialServicePort {
    Map<String, Long> registerVehicleEntry(VehicleModel vehicleModel, Long parkingId);
}
