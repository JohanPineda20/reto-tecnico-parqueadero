package com.nelumbo.parking.application.handler.impl;

import com.nelumbo.parking.application.dto.request.ParkingRequest;
import com.nelumbo.parking.application.dto.response.ParkingResponse;
import com.nelumbo.parking.application.handler.IParkingHandler;
import com.nelumbo.parking.application.mapper.ParkingDtoMapper;
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
    public List<ParkingResponse> getAllParkings(Integer page, Integer size) {
        return parkingServicePort.getAllParkings(page, size)
                .stream()
                .map(parkingDtoMapper::toParkingResponse)
                .collect(Collectors.toList());
    }
    @Override
    public List<ParkingResponse> getAllParkingsFromSocio(Integer page, Integer size) {
        return parkingServicePort.getAllParkingsFromSocio(page, size)
                .stream()
                .map(parkingDtoMapper::toParkingResponse)
                .collect(Collectors.toList());
    }
}
