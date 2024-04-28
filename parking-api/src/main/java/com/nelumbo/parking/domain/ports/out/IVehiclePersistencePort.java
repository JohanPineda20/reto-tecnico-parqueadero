package com.nelumbo.parking.domain.ports.out;

import com.nelumbo.parking.domain.model.VehicleModel;

public interface IVehiclePersistencePort {
    VehicleModel findByLicensePlate(String licensePlate);
    VehicleModel save(VehicleModel vehicleModel);
}
