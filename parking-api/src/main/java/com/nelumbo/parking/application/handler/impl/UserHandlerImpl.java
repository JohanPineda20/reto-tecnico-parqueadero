package com.nelumbo.parking.application.handler.impl;

import com.nelumbo.parking.application.dto.request.UserRequest;
import com.nelumbo.parking.application.dto.response.UserResponse;
import com.nelumbo.parking.application.handler.IUserHandler;
import com.nelumbo.parking.application.mapper.UserDtoMapper;
import com.nelumbo.parking.domain.ports.in.IUserServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserHandlerImpl implements IUserHandler {
    private final IUserServicePort userServicePort;
    private final UserDtoMapper userDtoMapper;
    @Override
    public UserResponse createSocio(UserRequest userRequest) {
        return userDtoMapper.toUserResponse(userServicePort.createSocio(userDtoMapper.toUserModel(userRequest)));
    }
}
