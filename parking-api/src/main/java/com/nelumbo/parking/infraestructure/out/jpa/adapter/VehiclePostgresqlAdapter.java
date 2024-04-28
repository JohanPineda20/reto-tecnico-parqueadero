package com.nelumbo.parking.infraestructure.out.jpa.adapter;

import com.nelumbo.parking.domain.model.VehicleModel;
import com.nelumbo.parking.domain.ports.out.IVehiclePersistencePort;
import com.nelumbo.parking.infraestructure.out.jpa.mapper.VehicleEntityMapper;
import com.nelumbo.parking.infraestructure.out.jpa.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class VehiclePostgresqlAdapter implements IVehiclePersistencePort {
    private final VehicleRepository vehicleRepository;
    private final VehicleEntityMapper vehicleEntityMapper;
    @Override
    public VehicleModel findByLicensePlate(String licensePlate) {
        return vehicleEntityMapper.toVehicleModel(vehicleRepository.findByLicensePlate(licensePlate).orElse(null));
    }

    @Override
    public VehicleModel save(VehicleModel vehicleModel) {
        return vehicleEntityMapper.toVehicleModel(vehicleRepository.save(vehicleEntityMapper.toVehicleEntity(vehicleModel)));
    }
}
