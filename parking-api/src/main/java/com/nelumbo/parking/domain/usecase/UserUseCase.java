package com.nelumbo.parking.domain.usecase;

import com.nelumbo.parking.domain.model.UserModel;
import com.nelumbo.parking.domain.ports.in.IUserServicePort;
import com.nelumbo.parking.domain.ports.out.IPasswordEncoderPort;
import com.nelumbo.parking.domain.ports.out.IRolePersistencePort;
import com.nelumbo.parking.domain.ports.out.IUserPersistencePort;
import com.nelumbo.parking.domain.utils.Constants;
import com.nelumbo.parking.domain.utils.exceptions.DataAlreadyExistsException;
import com.nelumbo.parking.domain.utils.exceptions.DataNotFoundException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserUseCase implements IUserServicePort {
    private final IUserPersistencePort userPersistencePort;
    private final IRolePersistencePort rolePersistencePort;
    private final IPasswordEncoderPort passwordEncoderPort;
    @Override
    public UserModel createSocio(UserModel userModel) {
        if(userPersistencePort.existsByDniNumber(userModel.getDni())){
            throw new DataAlreadyExistsException(String.format(Constants.USER_ALREADY_EXISTS, Constants.DNI, userModel.getDni()));
        }
        if(userPersistencePort.existsByEmail(userModel.getEmail())){
            throw new DataAlreadyExistsException(String.format(Constants.USER_ALREADY_EXISTS, Constants.EMAIL, userModel.getEmail()));
        }
        var role = rolePersistencePort.findById(Constants.ID_SOCIO);
        if(role == null) throw new DataNotFoundException(Constants.ROLE_NOT_FOUND);

        userModel.setRole(role);
        userModel.setPassword(passwordEncoderPort.encode(userModel.getPassword()));
        return userPersistencePort.save(userModel);
    }
    @Override
    public UserModel findById(Long id){
        var userModel = userPersistencePort.findById(id);
        if(userModel == null) throw new DataNotFoundException(Constants.USER_NOT_FOUND);
        return userModel;
    }
}
