package com.nelumbo.parking.domain.usecase;

import com.nelumbo.parking.domain.model.VehicleModel;
import com.nelumbo.parking.domain.ports.in.IVehicleServicePort;
import com.nelumbo.parking.domain.ports.out.IVehiclePersistencePort;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class VehicleUseCase implements IVehicleServicePort {
    private final IVehiclePersistencePort vehiclePersistencePort;
    @Override
    public VehicleModel getByLicensePlate(String licensePlate){
        return vehiclePersistencePort.findByLicensePlate(licensePlate);
    }

    @Override
    public VehicleModel save(VehicleModel vehicleModel) {
        return vehiclePersistencePort.save(vehicleModel);
    }
}
