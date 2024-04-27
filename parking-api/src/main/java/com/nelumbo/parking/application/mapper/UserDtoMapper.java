package com.nelumbo.parking.application.mapper;

import com.nelumbo.parking.application.dto.request.UserRequest;
import com.nelumbo.parking.application.dto.response.UserResponse;
import com.nelumbo.parking.domain.model.UserModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface UserDtoMapper {
    UserModel toUserModel (UserRequest userRequest);
    @Mapping(target="role", source="role.name")
    UserResponse toUserResponse(UserModel userModel);
}
