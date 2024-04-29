package com.nelumbo.parking.infraestructure.out.jpa.adapter;

import com.nelumbo.parking.domain.model.HistorialModel;
import com.nelumbo.parking.domain.ports.out.IHistorialPersistencePort;
import com.nelumbo.parking.infraestructure.out.jpa.mapper.HistorialEntityMapper;
import com.nelumbo.parking.infraestructure.out.jpa.repository.HistorialRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class HistorialPostgresqlAdapter implements IHistorialPersistencePort {

    private final HistorialRepository historialRepository;
    private final HistorialEntityMapper historialEntityMapper;

    @Override
    public List<HistorialModel> getAllHistorialByParkingIdAndDepartureDateIsNull(Long parkingId) {
        return historialRepository.findByParkingIdAndDepartureDateIsNull(parkingId)
                .stream()
                .map(historialEntityMapper::toHistorialModel)
                .collect(Collectors.toList());
    }
    @Override
    public HistorialModel findByVehicleIdAndDepartureDateIsNull(Long vehicleId) {
        return historialEntityMapper.toHistorialModel(historialRepository.findByVehicleIdAndDepartureDateIsNull(vehicleId));
    }
    @Override
    public HistorialModel save(HistorialModel historialModel) {
        return historialEntityMapper.toHistorialModel(historialRepository.save(historialEntityMapper.toHistorialEntity(historialModel)));
    }
    @Override
    public List<HistorialModel> getAllHistorialByParkingIdAndDepartureDateIsNullPagination(Integer page, Integer size, Long parkingId) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "vehicle.licensePlate"));
        return historialRepository.findByParkingIdAndDepartureDateIsNull(pageable, parkingId).getContent()
                .stream()
                .map(historialEntityMapper::toHistorialModel)
                .collect(Collectors.toList());
    }
    @Override
    public HistorialModel findByVehicleIdAndParkingIdAndDepartureDateIsNull(Long vehicleId, Long parkingId) {
        return historialEntityMapper.toHistorialModel(historialRepository.findByVehicleIdAndParkingIdAndDepartureDateIsNull(vehicleId, parkingId));
    }
}
