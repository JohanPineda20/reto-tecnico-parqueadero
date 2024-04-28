package com.nelumbo.parking.infraestructure.out.jpa.mapper;

import com.nelumbo.parking.domain.model.VehicleModel;
import com.nelumbo.parking.infraestructure.out.jpa.entity.VehicleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface VehicleEntityMapper {
    VehicleEntity toVehicleEntity(VehicleModel vehicleModel);
    VehicleModel toVehicleModel(VehicleEntity vehicleEntity);
}
