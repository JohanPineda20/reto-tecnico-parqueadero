package com.nelumbo.parking.domain.ports.in;

import com.nelumbo.parking.domain.model.ParkingModel;

import java.util.List;

public interface IParkingServicePort {
    ParkingModel createParking(ParkingModel parkingModel);
    ParkingModel updateParkingById(ParkingModel parkingModel, Long id);
    void deleteParkingById(Long id);
    ParkingModel getParkingById(Long id);
    List<ParkingModel> getAllParkings(Integer page, Integer size);
    List<ParkingModel> getAllParkingsFromSocio(Integer page, Integer size);
}
