package com.nelumbo.parking.domain.ports.out;

import com.nelumbo.parking.domain.model.UserModel;

public interface IUserPersistencePort {
    UserModel save(UserModel userModel);
    boolean existsByDniNumber(String dniNumber);
    boolean existsByEmail(String email);
    UserModel findById(Long id);
}
