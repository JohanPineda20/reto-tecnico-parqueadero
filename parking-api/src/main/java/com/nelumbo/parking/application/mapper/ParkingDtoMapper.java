package com.nelumbo.parking.application.mapper;

import com.nelumbo.parking.application.dto.request.ParkingRequest;
import com.nelumbo.parking.application.dto.response.ParkingResponse;
import com.nelumbo.parking.domain.model.ParkingModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface ParkingDtoMapper {
    @Mapping(target = "user.id", source = "userId")
    ParkingModel toParkingModel (ParkingRequest parkingRequest);
    @Mapping(target = "socioEmail", source = "user.email")
    ParkingResponse toParkingResponse(ParkingModel parkingModel);
}
