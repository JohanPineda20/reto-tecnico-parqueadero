package com.nelumbo.parking.domain.usecase;

import com.nelumbo.parking.domain.model.ParkingModel;
import com.nelumbo.parking.domain.model.UserModel;
import com.nelumbo.parking.domain.ports.in.IParkingServicePort;
import com.nelumbo.parking.domain.ports.in.IUserServicePort;
import com.nelumbo.parking.domain.ports.out.IAuthenticationInfoPort;
import com.nelumbo.parking.domain.ports.out.IParkingPersistencePort;
import com.nelumbo.parking.domain.utils.Constants;
import com.nelumbo.parking.domain.utils.exceptions.DataAlreadyExistsException;
import com.nelumbo.parking.domain.utils.exceptions.DataNotFoundException;
import com.nelumbo.parking.domain.utils.exceptions.DomainException;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
public class ParkingUseCase implements IParkingServicePort {

    private final IParkingPersistencePort parkingPersistencePort;
    private final IUserServicePort userServicePort;
    private final IAuthenticationInfoPort authenticationInfoPort;
    @Override
    public ParkingModel createParking(ParkingModel parkingModel) {
        validateIfExistsByName(parkingModel);
        var userModel = validateUserSocio(parkingModel.getUser().getId());
        parkingModel.setUser(userModel);
        return parkingPersistencePort.save(parkingModel);
    }

    @Override
    public ParkingModel updateParkingById(ParkingModel parkingModel, Long id) {
        var parkingModel1 = getParkingById(id);
        if(!Objects.equals(parkingModel1.getName(), parkingModel.getName())) validateIfExistsByName(parkingModel);
        parkingModel1.setName(parkingModel.getName());
        parkingModel1.setCapacity(parkingModel.getCapacity());
        parkingModel1.setCostPerHour(parkingModel.getCostPerHour());
        var userModel = validateUserSocio(parkingModel.getUser().getId());
        parkingModel1.setUser(userModel);
        return parkingPersistencePort.save(parkingModel1);
    }
    @Override
    public void deleteParkingById(Long id) {
        getParkingById(id);
        parkingPersistencePort.deleteParkingById(id);
    }
    @Override
    public ParkingModel getParkingById(Long id) {
        var parkingModel = parkingPersistencePort.findById(id);
        if(parkingModel == null) throw new DataNotFoundException(Constants.PARKING_NOT_FOUND);
        return parkingModel;
    }
    @Override
    public List<ParkingModel> getAllParkings(Integer page, Integer size) {
        return parkingPersistencePort.getAllParkings(page, size);
    }
    @Override
    public List<ParkingModel> getAllParkingsFromSocio(Integer page, Integer size) {
        Long socioId = authenticationInfoPort.getIdFromAuthentication();
        return parkingPersistencePort.getAllParkingsFromSocio(page, size, socioId);
    }

    private UserModel validateUserSocio(Long userId) {
        var userModel = userServicePort.findById(userId);
        if(!userModel.getRole().getId().equals(Constants.ID_SOCIO)) throw new DomainException(Constants.USER_MUST_BE_SOCIO);
        return userModel;
    }
    private void validateIfExistsByName(ParkingModel parkingModel) {
        if(parkingPersistencePort.existsByName(parkingModel.getName())) throw new DataAlreadyExistsException(Constants.PARKING_ALREADY_EXISTS);
    }
}
