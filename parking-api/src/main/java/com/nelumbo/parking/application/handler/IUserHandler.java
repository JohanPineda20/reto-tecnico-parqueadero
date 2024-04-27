package com.nelumbo.parking.application.handler;

import com.nelumbo.parking.application.dto.request.UserRequest;
import com.nelumbo.parking.application.dto.response.UserResponse;

public interface IUserHandler {
    UserResponse createSocio(UserRequest userRequest);
}
