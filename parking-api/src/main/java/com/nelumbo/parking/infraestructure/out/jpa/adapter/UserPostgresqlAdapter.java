package com.nelumbo.parking.infraestructure.out.jpa.adapter;

import com.nelumbo.parking.domain.model.UserModel;
import com.nelumbo.parking.domain.ports.out.IUserPersistencePort;
import com.nelumbo.parking.infraestructure.out.jpa.mapper.UserEntityMapper;
import com.nelumbo.parking.infraestructure.out.jpa.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserPostgresqlAdapter implements IUserPersistencePort {

    private final UserRepository userRepository;
    private final UserEntityMapper userEntityMapper;
    @Override
    public UserModel save(UserModel userModel) {
        return userEntityMapper.toUserModel(userRepository.save(userEntityMapper.toUserEntity(userModel)));
    }
    @Override
    public boolean existsByDniNumber(String dni) {
        return userRepository.existsByDni(dni);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}
