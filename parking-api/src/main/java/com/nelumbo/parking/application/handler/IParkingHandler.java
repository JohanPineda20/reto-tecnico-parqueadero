package com.nelumbo.parking.application.handler;

import com.nelumbo.parking.application.dto.request.ParkingRequest;
import com.nelumbo.parking.application.dto.response.ParkingResponse;

import java.util.List;

public interface IParkingHandler {
    ParkingResponse createParking(ParkingRequest parkingRequest);
    ParkingResponse updateParkingById(ParkingRequest parkingRequest, Long id);
    void deleteParkingById(Long id);
    ParkingResponse getParkingById(Long id);
    List<ParkingResponse> getAllParkings(Integer page, Integer size);
    List<ParkingResponse> getAllParkingsFromSocio(Integer page, Integer size);
}
