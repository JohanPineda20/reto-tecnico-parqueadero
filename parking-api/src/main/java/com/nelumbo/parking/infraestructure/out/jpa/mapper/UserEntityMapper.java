package com.nelumbo.parking.infraestructure.out.jpa.mapper;

import com.nelumbo.parking.domain.model.UserModel;
import com.nelumbo.parking.infraestructure.out.jpa.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface UserEntityMapper {
    UserEntity toUserEntity(UserModel userModel);

    UserModel toUserModel(UserEntity userEntity);
}
