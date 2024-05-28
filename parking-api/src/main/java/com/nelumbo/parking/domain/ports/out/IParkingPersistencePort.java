package com.nelumbo.parking.domain.ports.out;

import com.nelumbo.parking.domain.model.CustomPage;
import com.nelumbo.parking.domain.model.ParkingModel;

import java.util.List;

public interface IParkingPersistencePort {
    ParkingModel save(ParkingModel parkingModel);
    boolean existsByName(String name);
    ParkingModel findById(Long id);
    void deleteParkingById(Long id);
    CustomPage<ParkingModel> getAllParkings(Integer page, Integer size);
    CustomPage<ParkingModel> getAllParkingsFromSocio(Integer page, Integer size, Long socioId);
}
