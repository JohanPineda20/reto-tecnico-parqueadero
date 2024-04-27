package com.nelumbo.parking.domain.ports.in;

import com.nelumbo.parking.domain.model.UserModel;

public interface IUserServicePort {
    UserModel createSocio(UserModel userModel);
    UserModel findById(Long id);
}
