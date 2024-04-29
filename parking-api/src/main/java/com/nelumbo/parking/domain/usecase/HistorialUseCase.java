package com.nelumbo.parking.domain.usecase;

import com.nelumbo.parking.domain.model.HistorialModel;
import com.nelumbo.parking.domain.model.ParkingModel;
import com.nelumbo.parking.domain.model.VehicleModel;
import com.nelumbo.parking.domain.ports.in.IHistorialServicePort;
import com.nelumbo.parking.domain.ports.in.IParkingServicePort;
import com.nelumbo.parking.domain.ports.in.IVehicleServicePort;
import com.nelumbo.parking.domain.ports.out.IAuthenticationInfoPort;
import com.nelumbo.parking.domain.ports.out.IHistorialPersistencePort;
import com.nelumbo.parking.domain.utils.Constants;
import com.nelumbo.parking.domain.utils.exceptions.DataAlreadyExistsException;
import com.nelumbo.parking.domain.utils.exceptions.DomainException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
public class HistorialUseCase implements IHistorialServicePort {

    private final IHistorialPersistencePort historialPersistencePort;
    private final IParkingServicePort parkingServicePort;
    private final IVehicleServicePort vehicleServicePort;
    private final IAuthenticationInfoPort authenticationInfoPort;

    @Override
    public Map<String, Long> registerVehicleEntry(VehicleModel vehicleModel, Long parkingId) {
        Long socioId = authenticationInfoPort.getIdFromAuthentication();
        ParkingModel parkingModel = parkingServicePort.getParkingById(parkingId);
        if(!Objects.equals(socioId, parkingModel.getUser().getId())) throw new DomainException(Constants.USER_IS_NOT_PARKING_SOCIO);

        VehicleModel vehicleModel1 = vehicleServicePort.getByLicensePlate(vehicleModel.getLicensePlate());
        if(vehicleModel1 != null && historialPersistencePort.findByVehicleIdAndDepartureDateIsNull(vehicleModel1.getId()) != null) {
            throw new DataAlreadyExistsException(Constants.VEHICLE_IS_IN_THE_PARKING);
        }

        List<HistorialModel> historialModelList = historialPersistencePort.getAllHistorialByParkingIdAndDepartureDateIsNull(parkingId);
        if(parkingModel.getCapacity() - historialModelList.size() <= Constants.ZERO) throw new DomainException(Constants.PARKING_IS_FULL);

        if(vehicleModel1 == null) {
            vehicleModel1 = vehicleServicePort.save(vehicleModel);
        }
        HistorialModel historialModel = new HistorialModel();
        historialModel.setParking(parkingModel);
        historialModel.setVehicle(vehicleModel1);
        historialModel.setEntryDate(LocalDateTime.now());
        Long historialId = historialPersistencePort.save(historialModel).getId();

        return Collections.singletonMap("id", historialId);
    }
    @Override
    public List<HistorialModel> getAllVehiclesInParking(Integer page, Integer size, Long parkingId){
        String rol = authenticationInfoPort.getRolFromAuthentication();
        ParkingModel parkingModel = parkingServicePort.getParkingById(parkingId);
        if(Objects.equals(rol, Constants.SOCIO)) {
            Long socioId = authenticationInfoPort.getIdFromAuthentication();
            if(!Objects.equals(socioId, parkingModel.getUser().getId())) throw new DomainException(Constants.USER_IS_NOT_PARKING_SOCIO);
        }
        return historialPersistencePort.getAllHistorialByParkingIdAndDepartureDateIsNullPagination(page, size, parkingId);
    }
}
