package com.nelumbo.parking.infraestructure.out.jpa.mapper;

import com.nelumbo.parking.domain.model.ParkingModel;
import com.nelumbo.parking.infraestructure.out.jpa.entity.ParkingEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface ParkingEntityMapper {
    ParkingModel toParkingModel(ParkingEntity parkingEntity);
    ParkingEntity toParkingEntity(ParkingModel parkingModel);
}
