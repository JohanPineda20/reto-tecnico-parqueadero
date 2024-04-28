package com.nelumbo.parking.domain.ports.in;

import com.nelumbo.parking.domain.model.VehicleModel;

public interface IVehicleServicePort {
    VehicleModel getByLicensePlate(String licensePlate);
    VehicleModel save(VehicleModel vehicleModel);
}