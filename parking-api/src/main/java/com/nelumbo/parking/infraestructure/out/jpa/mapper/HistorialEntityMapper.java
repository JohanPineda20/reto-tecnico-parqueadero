package com.nelumbo.parking.infraestructure.out.jpa.mapper;

import com.nelumbo.parking.domain.model.HistorialModel;
import com.nelumbo.parking.infraestructure.out.jpa.entity.HistorialEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface HistorialEntityMapper {
    HistorialEntity toHistorialEntity(HistorialModel historialModel);
    HistorialModel toHistorialModel(HistorialEntity historialEntity);
}
