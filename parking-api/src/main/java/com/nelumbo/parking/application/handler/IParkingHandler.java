package com.nelumbo.parking.application.handler;

import com.nelumbo.parking.application.dto.request.ParkingRequest;
import com.nelumbo.parking.application.dto.response.HistorialResponse;
import com.nelumbo.parking.application.dto.response.ParkingResponse;
import com.nelumbo.parking.domain.model.CustomPage;

import java.util.List;

public interface IParkingHandler {
    ParkingResponse createParking(ParkingRequest parkingRequest);
    ParkingResponse updateParkingById(ParkingRequest parkingRequest, Long id);
    void deleteParkingById(Long id);
    ParkingResponse getParkingById(Long id);
    CustomPage<ParkingResponse> getAllParkings(Integer page, Integer size);
    List<HistorialResponse> getAllVehiclesInParking(Integer page, Integer size, Long parkingId);
}
