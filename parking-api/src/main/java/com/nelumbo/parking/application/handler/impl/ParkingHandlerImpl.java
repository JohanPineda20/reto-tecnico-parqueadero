package com.nelumbo.parking.application.handler.impl;

import com.nelumbo.parking.application.dto.request.ParkingRequest;
import com.nelumbo.parking.application.dto.response.HistorialResponse;
import com.nelumbo.parking.application.dto.response.ParkingResponse;
import com.nelumbo.parking.application.dto.response.VehicleResponse;
import com.nelumbo.parking.application.handler.IParkingHandler;
import com.nelumbo.parking.application.mapper.ParkingDtoMapper;
import com.nelumbo.parking.domain.model.CustomPage;
import com.nelumbo.parking.domain.model.ParkingModel;
import com.nelumbo.parking.domain.ports.in.IHistorialServicePort;
import com.nelumbo.parking.domain.ports.in.IParkingServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ParkingHandlerImpl implements IParkingHandler {
    private final IParkingServicePort parkingServicePort;
    private final ParkingDtoMapper parkingDtoMapper;
    private final IHistorialServicePort historialServicePort;
    @Override
    public ParkingResponse createParking(ParkingRequest parkingRequest) {
        return parkingDtoMapper.toParkingResponse(parkingServicePort.createParking(parkingDtoMapper.toParkingModel(parkingRequest)));
    }
    @Override
    public ParkingResponse updateParkingById(ParkingRequest parkingRequest, Long id) {
        return parkingDtoMapper.toParkingResponse(parkingServicePort.updateParkingById(parkingDtoMapper.toParkingModel(parkingRequest),id));
    }
    @Override
    public void deleteParkingById(Long id) {
        parkingServicePort.deleteParkingById(id);
    }
    @Override
    public ParkingResponse getParkingById(Long id) {
        return parkingDtoMapper.toParkingResponse(parkingServicePort.getParkingById(id));
    }
    @Override
    public CustomPage<ParkingResponse> getAllParkings(Integer page, Integer size) {
        CustomPage<ParkingModel> pageParkingModel = parkingServicePort.getAllParkings(page, size);
        List<ParkingResponse> parkingResponseList = pageParkingModel.getContent()
                .stream()
                .map(parkingDtoMapper::toParkingResponse)
                .collect(Collectors.toList());
        return new CustomPage<>(
                parkingResponseList,
                pageParkingModel.getNumber(),
                pageParkingModel.getSize(),
                pageParkingModel.getTotalElements(),
                pageParkingModel.getTotalPages(),
                pageParkingModel.isLast(),
                pageParkingModel.isFirst()
        );
    }
    @Override
    public List<HistorialResponse> getAllVehiclesInParking(Integer page, Integer size, Long parkingId) {
        return historialServicePort.getAllVehiclesInParking(page, size, parkingId)
                .stream()
                .map(historialModel -> {
                    VehicleResponse vehicleResponse = new VehicleResponse();
                    vehicleResponse.setId(historialModel.getVehicle().getId());
                    vehicleResponse.setLicensePlate(historialModel.getVehicle().getLicensePlate());
                    HistorialResponse historialResponse = new HistorialResponse();
                    historialResponse.setVehicle(vehicleResponse);
                    historialResponse.setEntryDate(historialModel.getEntryDate());
                    return historialResponse;
                })
                .collect(Collectors.toList());
    }
}
