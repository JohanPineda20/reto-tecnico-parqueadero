package com.nelumbo.parking.infraestructure.out.jpa.adapter;

import com.nelumbo.parking.domain.model.CustomPage;
import com.nelumbo.parking.domain.model.ParkingModel;
import com.nelumbo.parking.domain.ports.out.IParkingPersistencePort;
import com.nelumbo.parking.infraestructure.out.jpa.mapper.ParkingEntityMapper;
import com.nelumbo.parking.infraestructure.out.jpa.repository.ParkingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@RequiredArgsConstructor
public class ParkingPostgresqlAdapter implements IParkingPersistencePort {
    private final ParkingRepository parkingRepository;
    private final ParkingEntityMapper parkingEntityMapper;
    @Override
    public ParkingModel save(ParkingModel parkingModel) {
        return parkingEntityMapper.toParkingModel(parkingRepository.save(parkingEntityMapper.toParkingEntity(parkingModel)));
    }

    @Override
    public boolean existsByName(String name) {
        return parkingRepository.existsByName(name);
    }

    @Override
    public ParkingModel findById(Long id) {
        return parkingEntityMapper.toParkingModel(parkingRepository.findById(id).orElse(null));
    }

    @Override
    public void deleteParkingById(Long id) {
        parkingRepository.deleteById(id);
    }

    @Override
    public CustomPage<ParkingModel> getAllParkings(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "name"));
        Page<ParkingModel> pageParkingModel = parkingRepository.findAll(pageable).map(parkingEntityMapper::toParkingModel);
        return new CustomPage<>(
                pageParkingModel.getContent(),
                pageParkingModel.getNumber(),
                pageParkingModel.getSize(),
                pageParkingModel.getTotalElements(),
                pageParkingModel.getTotalPages(),
                pageParkingModel.isLast(),
                pageParkingModel.isFirst()
        );
    }

    @Override
    public CustomPage<ParkingModel> getAllParkingsFromSocio(Integer page, Integer size, Long socioId) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "name"));
        Page<ParkingModel> pageParkingModel = parkingRepository.findAllByUserId(pageable, socioId).map(parkingEntityMapper::toParkingModel);
        return new CustomPage<>(
                pageParkingModel.getContent(),
                pageParkingModel.getNumber(),
                pageParkingModel.getSize(),
                pageParkingModel.getTotalElements(),
                pageParkingModel.getTotalPages(),
                pageParkingModel.isLast(),
                pageParkingModel.isFirst()
        );
    }
}
