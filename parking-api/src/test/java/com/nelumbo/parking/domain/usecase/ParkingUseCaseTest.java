package com.nelumbo.parking.domain.usecase;

import com.nelumbo.parking.domain.model.ParkingModel;
import com.nelumbo.parking.domain.model.RoleModel;
import com.nelumbo.parking.domain.model.UserModel;
import com.nelumbo.parking.domain.ports.in.IUserServicePort;
import com.nelumbo.parking.domain.ports.out.IAuthenticationInfoPort;
import com.nelumbo.parking.domain.ports.out.IParkingPersistencePort;
import com.nelumbo.parking.domain.utils.exceptions.DataAlreadyExistsException;
import com.nelumbo.parking.domain.utils.exceptions.DataNotFoundException;
import com.nelumbo.parking.domain.utils.exceptions.DomainException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ParkingUseCaseTest {
    @Mock
    private IParkingPersistencePort parkingPersistencePort;
    @Mock
    private IUserServicePort userServicePort;
    @Mock
    private IAuthenticationInfoPort authenticationInfoPort;
    @InjectMocks
    private ParkingUseCase parkingUseCase;

    @Test
    void createParking() {
        //given
        ParkingModel parkingModel = new ParkingModel();
        UserModel userModel = new UserModel();
        RoleModel roleModel = new RoleModel();
        roleModel.setId(2L); //socio
        userModel.setId(1L);
        userModel.setRole(roleModel);
        parkingModel.setName("example");
        parkingModel.setUser(userModel);
        when(parkingPersistencePort.existsByName(parkingModel.getName())).thenReturn(false);
        when(userServicePort.findById(userModel.getId())).thenReturn(userModel);
        when(parkingPersistencePort.save(parkingModel)).thenReturn(parkingModel);

        //when
        var parkingModel1 = parkingUseCase.createParking(parkingModel);

        //then
        assertNotNull(parkingModel1);
        assertEquals(parkingModel.getName(), parkingModel1.getName());
        assertEquals(parkingModel.getUser().getId(), parkingModel1.getUser().getId());
        assertEquals(parkingModel.getUser().getRole().getId(), parkingModel1.getUser().getRole().getId());
        verify(parkingPersistencePort, times(1)).save(parkingModel);
    }
    @Test
    void createParkingWithExistingName() {
        //given
        ParkingModel parkingModel = new ParkingModel();
        parkingModel.setName("example");
        when(parkingPersistencePort.existsByName(parkingModel.getName())).thenReturn(true);

        //when
        assertThrows(DataAlreadyExistsException.class, () -> parkingUseCase.createParking(parkingModel));

        //then
        verify(parkingPersistencePort, times(0)).save(parkingModel);
    }
    @Test
    void createParkingWithInvalidUser() {
        //given
        ParkingModel parkingModel = new ParkingModel();
        UserModel userModel = new UserModel();
        RoleModel roleModel = new RoleModel();
        roleModel.setId(1L); //admin
        userModel.setId(1L);
        userModel.setRole(roleModel);
        parkingModel.setName("example");
        parkingModel.setUser(userModel);
        when(parkingPersistencePort.existsByName(parkingModel.getName())).thenReturn(false);
        when(userServicePort.findById(userModel.getId())).thenReturn(userModel);

        //when
        assertThrows(DomainException.class, () -> parkingUseCase.createParking(parkingModel));

        //then
        verify(parkingPersistencePort, times(0)).save(parkingModel);
    }

    @Test
    void updateParkingById() {
        //given
        ParkingModel parkingModel = new ParkingModel();
        UserModel userModel = new UserModel();
        RoleModel roleModel = new RoleModel();
        roleModel.setId(2L); //socio
        userModel.setId(1L);
        userModel.setRole(roleModel);
        parkingModel.setName("example");
        parkingModel.setUser(userModel);
        parkingModel.setCapacity(5L);
        parkingModel.setCostPerHour(BigDecimal.valueOf(5));
        when(parkingPersistencePort.findById(1L)).thenReturn(parkingModel);
        when(userServicePort.findById(userModel.getId())).thenReturn(userModel);
        when(parkingPersistencePort.save(parkingModel)).thenReturn(parkingModel);

        //when
        var parkingModelResult = parkingUseCase.updateParkingById(parkingModel, 1L);

        //then
        assertNotNull(parkingModelResult);
        assertEquals(parkingModel.getName(), parkingModelResult.getName());
        assertEquals(parkingModel.getUser().getId(), parkingModelResult.getUser().getId());
        assertEquals(parkingModel.getUser().getRole().getId(), parkingModelResult.getUser().getRole().getId());
        assertEquals(parkingModel.getCapacity(), parkingModelResult.getCapacity());
        assertEquals(parkingModel.getCostPerHour(), parkingModelResult.getCostPerHour());
        verify(parkingPersistencePort, times(1)).save(parkingModel);
    }

    @Test
    void updateParkingByIdWithExistingName() {
        //given
        ParkingModel parkingModel = new ParkingModel();
        parkingModel.setName("example");
        ParkingModel parkingModel1 = new ParkingModel();
        parkingModel1.setName("example1");
        when(parkingPersistencePort.findById(1L)).thenReturn(parkingModel1);
        when(parkingPersistencePort.existsByName(parkingModel.getName())).thenReturn(true);

        //when
        assertThrows(DataAlreadyExistsException.class, () -> parkingUseCase.updateParkingById(parkingModel, 1L));

        //then
        verify(parkingPersistencePort, times(0)).save(parkingModel);
    }

    @Test
    void updateParkingByIdWithInvalidUser() {
        //given
        ParkingModel parkingModel = new ParkingModel();
        UserModel userModel = new UserModel();
        RoleModel roleModel = new RoleModel();
        roleModel.setId(1L); //socio
        userModel.setId(1L);
        userModel.setRole(roleModel);
        parkingModel.setName("example");
        parkingModel.setUser(userModel);
        parkingModel.setCapacity(5L);
        parkingModel.setCostPerHour(BigDecimal.valueOf(5));
        when(parkingPersistencePort.findById(1L)).thenReturn(parkingModel);
        when(userServicePort.findById(userModel.getId())).thenReturn(userModel);

        //when
        assertThrows(DomainException.class, () -> parkingUseCase.updateParkingById(parkingModel, 1L));

        //then
        verify(parkingPersistencePort, times(0)).save(parkingModel);
    }

    @Test
    void deleteParkingById() {
        //given
        ParkingModel parkingModel = new ParkingModel();
        when(parkingPersistencePort.findById(1L)).thenReturn(parkingModel);

        //when
        parkingUseCase.deleteParkingById(1L);

        //then
        verify(parkingPersistencePort, times(1)).deleteParkingById(1L);
    }

    @Test
    void getParkingById() {
        //given
        ParkingModel parkingModel = new ParkingModel();
        when(parkingPersistencePort.findById(1L)).thenReturn(parkingModel);

        //when
        var parkingModelResult = parkingUseCase.getParkingById(1L);

        //then
        assertNotNull(parkingModelResult);
        assertEquals(parkingModel, parkingModelResult);
    }
    @Test
    void getParkingById_notFound() {
        //given
        when(parkingPersistencePort.findById(1L)).thenReturn(null);

        //when
        assertThrows(DataNotFoundException.class, () -> parkingUseCase.getParkingById(1L));

        //then
        verify(parkingPersistencePort, times(1)).findById(1L);
    }

    @Test
    void getAllParkings() {
        //given
        int page = 0;
        int size = 10;
        String role = "ADMIN";
        List<ParkingModel> parkingModelList = Collections.emptyList();
        when(authenticationInfoPort.getRolFromAuthentication()).thenReturn(role);
        when(parkingPersistencePort.getAllParkings(page, size)).thenReturn(parkingModelList);

        //when
        var parkingModelListResult = parkingUseCase.getAllParkings(page, size);

        //then
        assertNotNull(parkingModelListResult);
        assertEquals(parkingModelList, parkingModelListResult);
        verify(authenticationInfoPort, times(0)).getIdFromAuthentication();
    }
    @Test
    void getAllParkingsFromSocio() {
        //given
        int page = 0;
        int size = 10;
        String role = "SOCIO";
        List<ParkingModel> parkingModelList = Collections.emptyList();
        when(authenticationInfoPort.getRolFromAuthentication()).thenReturn(role);
        when(parkingPersistencePort.getAllParkingsFromSocio(page, size, 0L)).thenReturn(parkingModelList);

        //when
        var parkingModelListResult = parkingUseCase.getAllParkings(page, size);

        //then
        assertNotNull(parkingModelListResult);
        assertEquals(parkingModelList, parkingModelListResult);
        verify(parkingPersistencePort, times(0)).getAllParkings(page, size);
    }
}