package com.nelumbo.parking.infraestructure.out.jpa.mapper;

import com.nelumbo.parking.domain.model.RoleModel;
import com.nelumbo.parking.infraestructure.out.jpa.entity.RoleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface RoleEntityMapper {
    RoleModel toRolModel(RoleEntity rolEntity);
}
