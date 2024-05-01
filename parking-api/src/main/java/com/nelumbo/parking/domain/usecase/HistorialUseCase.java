package com.nelumbo.parking.domain.usecase;

import com.nelumbo.parking.domain.model.HistorialModel;
import com.nelumbo.parking.domain.model.MessageModel;
import com.nelumbo.parking.domain.model.ParkingModel;
import com.nelumbo.parking.domain.model.VehicleModel;
import com.nelumbo.parking.domain.ports.in.IHistorialServicePort;
import com.nelumbo.parking.domain.ports.in.IParkingServicePort;
import com.nelumbo.parking.domain.ports.in.IVehicleServicePort;
import com.nelumbo.parking.domain.ports.out.IAuthenticationInfoPort;
import com.nelumbo.parking.domain.ports.out.IHistorialPersistencePort;
import com.nelumbo.parking.domain.ports.out.IMessageServicePort;
import com.nelumbo.parking.domain.utils.Constants;
import com.nelumbo.parking.domain.utils.exceptions.DataAlreadyExistsException;
import com.nelumbo.parking.domain.utils.exceptions.DomainException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
public class HistorialUseCase implements IHistorialServicePort {

    private final IHistorialPersistencePort historialPersistencePort;
    private final IParkingServicePort parkingServicePort;
    private final IVehicleServicePort vehicleServicePort;
    private final IAuthenticationInfoPort authenticationInfoPort;
    private final IMessageServicePort messageServicePort;

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

        sendMessage(Collections.singletonList(parkingModel.getUser().getEmail()),
                Constants.SUBJECT_ENTRY,
                String.format(Constants.MESSAGE_ENTRY, vehicleModel.getLicensePlate(), parkingModel.getName()));

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
    @Override
    public Map<String, String> registerVehicleDeparture(String licensePlate, Long parkingId) {
        Long socioId = authenticationInfoPort.getIdFromAuthentication();
        ParkingModel parkingModel = parkingServicePort.getParkingById(parkingId);
        if(!Objects.equals(socioId, parkingModel.getUser().getId())) throw new DomainException(Constants.USER_IS_NOT_PARKING_SOCIO);

        VehicleModel vehicleModel = vehicleServicePort.getByLicensePlate(licensePlate);
        if (vehicleModel == null) {
            throw new DomainException(Constants.VEHICLE_IS_NOT_THE_PARKING);
        }
        HistorialModel historialModel = historialPersistencePort.findByVehicleIdAndParkingIdAndDepartureDateIsNull(vehicleModel.getId(), parkingId);
        if (historialModel == null) {
            throw new DomainException(Constants.VEHICLE_IS_NOT_THE_PARKING);
        }

        LocalDateTime entryDate = historialModel.getEntryDate();
        LocalDateTime departureDate = LocalDateTime.now();
        Double hours = calculateTime(entryDate, departureDate);
        BigDecimal payment =  BigDecimal.valueOf(hours).multiply(parkingModel.getCostPerHour());

        historialModel.setDepartureDate(departureDate);
        historialModel.setPayment(payment);
        historialPersistencePort.save(historialModel);

        sendMessage(Collections.singletonList(parkingModel.getUser().getEmail()),
                Constants.SUBJECT_DEPARTURE,
                String.format(Constants.MESSAGE_DEPARTURE, licensePlate, parkingModel.getName()));

        return Collections.singletonMap("mensaje","Salida registrada");
    }
    @Override
    public List<Object[]> getTop10MostParkedVehicles() {
        String rol = authenticationInfoPort.getRolFromAuthentication();
        if(Objects.equals(rol, Constants.SOCIO)) {
            Long socioId = authenticationInfoPort.getIdFromAuthentication();
            return historialPersistencePort.getTop10MostParkedVehiclesInSocioParkings(socioId);
        }
        return historialPersistencePort.getTop10MostParkedVehicles();
    }
    @Override
    public List<Object[]> getTop10MostParkedVehiclesByParking(Long parkingId) {
        String rol = authenticationInfoPort.getRolFromAuthentication();
        ParkingModel parkingModel = parkingServicePort.getParkingById(parkingId);
        if(Objects.equals(rol, Constants.SOCIO)) {
            Long socioId = authenticationInfoPort.getIdFromAuthentication();
            if(!Objects.equals(socioId, parkingModel.getUser().getId())) throw new DomainException(Constants.USER_IS_NOT_PARKING_SOCIO);
        }
        return historialPersistencePort.getTop10MostParkedVehiclesByParking(parkingId);
    }
    @Override
    public List<Object[]> getFirstTimeParkedVehiclesByParking(Long parkingId) {
        String rol = authenticationInfoPort.getRolFromAuthentication();
        ParkingModel parkingModel = parkingServicePort.getParkingById(parkingId);
        if(Objects.equals(rol, Constants.SOCIO)) {
            Long socioId = authenticationInfoPort.getIdFromAuthentication();
            if(!Objects.equals(socioId, parkingModel.getUser().getId())) throw new DomainException(Constants.USER_IS_NOT_PARKING_SOCIO);
        }
        return historialPersistencePort.getFirstTimeParkedVehiclesByParking(parkingId);
    }
    @Override
    public List<HistorialModel> getVehicleByLicensePlate(String licensePlate) {
        String rol = authenticationInfoPort.getRolFromAuthentication();
        if(Objects.equals(rol, Constants.SOCIO)) {
            Long socioId = authenticationInfoPort.getIdFromAuthentication();
            return historialPersistencePort.getVehicleByLicensePlateInSocioParkings(socioId, licensePlate);
        }
        return historialPersistencePort.getVehicleByLicensePlate(licensePlate);
    }
    @Override
    public Map<String, BigDecimal> getCashIncomeByParking(Long parkingId){
        Long socioId = authenticationInfoPort.getIdFromAuthentication();
        ParkingModel parkingModel = parkingServicePort.getParkingById(parkingId);
        if(!Objects.equals(socioId, parkingModel.getUser().getId())) throw new DomainException(Constants.USER_IS_NOT_PARKING_SOCIO);
        return historialPersistencePort.getCashIncomeByParking(parkingId, LocalDate.now());
    }

    private Double calculateTime(LocalDateTime entryDate, LocalDateTime departureDate) {
        Duration duration = Duration.between(entryDate, departureDate);
        return duration.toMinutes() / 60.0;
    }
    private void sendMessage(List<String> to, String subject, String message) {
        MessageModel messageModel = new MessageModel();
        messageModel.setTo(to);
        messageModel.setSubject(subject);
        messageModel.setMessage(message);
        try {
            messageServicePort.sendMessage(messageModel);
            log.info("message sent");
        }catch (Exception e) {
            log.error("correo-api error: ".concat(e.getMessage()));
        }
    }
}
