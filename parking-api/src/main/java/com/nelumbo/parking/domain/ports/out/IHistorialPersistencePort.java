package com.nelumbo.parking.domain.ports.out;

import com.nelumbo.parking.domain.model.HistorialModel;

import java.util.List;

public interface IHistorialPersistencePort {
    List<HistorialModel> getAllHistorialByParkingIdAndDepartureDateIsNull(Long parkingId);
    HistorialModel findByVehicleIdAndDepartureDateIsNull(Long vehicleId);
    HistorialModel save(HistorialModel historialModel);
    List<HistorialModel> getAllHistorialByParkingIdAndDepartureDateIsNullPagination(Integer page, Integer size, Long parkingId);
    HistorialModel findByVehicleIdAndParkingIdAndDepartureDateIsNull(Long vehicleId, Long parkingId);
}
