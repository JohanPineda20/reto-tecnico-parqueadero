package com.nelumbo.parking.domain.usecase;

import com.nelumbo.parking.domain.model.VehicleModel;
import com.nelumbo.parking.domain.ports.out.IVehiclePersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VehicleUseCaseTest {
    @Mock
    private IVehiclePersistencePort vehiclePersistencePort;
    @InjectMocks
    private VehicleUseCase vehicleUseCase;
    VehicleModel vehicleModel;

    @BeforeEach
    void setUp() {
        vehicleModel = new VehicleModel();
        vehicleModel.setLicensePlate("ABC123");
    }

    @Test
    void getByLicensePlate() {
        //given
        when(vehiclePersistencePort.findByLicensePlate(vehicleModel.getLicensePlate())).thenReturn(vehicleModel);

        //when
        var vehicleResult = vehicleUseCase.getByLicensePlate(vehicleModel.getLicensePlate());

        //then
        assertEquals(vehicleModel, vehicleResult);
        verify(vehiclePersistencePort, times(1)).findByLicensePlate(vehicleModel.getLicensePlate());
    }

    @Test
    void save() {
        //given
        when(vehiclePersistencePort.save(vehicleModel)).thenReturn(vehicleModel);

        //when
        var vehicleResult = vehicleUseCase.save(vehicleModel);

        //then
        assertEquals(vehicleModel, vehicleResult);
        verify(vehiclePersistencePort, times(1)).save(vehicleModel);
    }
}