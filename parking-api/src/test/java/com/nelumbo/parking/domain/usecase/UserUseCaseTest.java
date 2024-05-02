package com.nelumbo.parking.domain.usecase;

import com.nelumbo.parking.domain.model.RoleModel;
import com.nelumbo.parking.domain.model.UserModel;
import com.nelumbo.parking.domain.ports.out.IPasswordEncoderPort;
import com.nelumbo.parking.domain.ports.out.IRolePersistencePort;
import com.nelumbo.parking.domain.ports.out.IUserPersistencePort;
import com.nelumbo.parking.domain.utils.exceptions.DataAlreadyExistsException;
import com.nelumbo.parking.domain.utils.exceptions.DataNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserUseCaseTest {
    @Mock
    private IUserPersistencePort userPersistencePort;
    @Mock
    private IRolePersistencePort rolePersistencePort;
    @Mock
    private IPasswordEncoderPort passwordEncoderPort;
    @InjectMocks
    private UserUseCase userUseCase;
    UserModel userModel;

    @BeforeEach
    void setUp() {
        userModel = new UserModel();
        userModel.setDni("1234");
        userModel.setEmail("admin@mail.com");
        userModel.setPassword("admin");
    }

    @Test
    void createSocio() {
        //given
        RoleModel roleModel = new RoleModel();
        when(userPersistencePort.existsByDniNumber(userModel.getDni())).thenReturn(false);
        when(userPersistencePort.existsByEmail(userModel.getEmail())).thenReturn(false);
        when(rolePersistencePort.findById(2L)).thenReturn(roleModel);
        when(passwordEncoderPort.encode(userModel.getPassword())).thenReturn("encrypted");
        when(userPersistencePort.save(userModel)).thenReturn(userModel);

        //when
        var socio = userUseCase.createSocio(userModel);

        //then
        assertNotNull(socio);
        assertEquals(userModel.getDni(), socio.getDni());
        assertEquals(userModel.getEmail(), socio.getEmail());
        assertEquals("encrypted", socio.getPassword());
        verify(userPersistencePort, times(1)).save(userModel);
    }

    @Test
    void createSocio_dniAlreadyExists() {
        //given
        when(userPersistencePort.existsByDniNumber(userModel.getDni())).thenReturn(true);

        //when
        assertThrows(DataAlreadyExistsException.class, () -> userUseCase.createSocio(userModel));

        //then
        verify(userPersistencePort, never()).save(userModel);
    }

    @Test
    void createSocio_emailAlreadyExists() {
        //given
        when(userPersistencePort.existsByEmail(userModel.getEmail())).thenReturn(true);

        //when
        assertThrows(DataAlreadyExistsException.class, () -> userUseCase.createSocio(userModel));

        //then
        verify(userPersistencePort, never()).save(userModel);
    }

    @Test
    void findById() {
        //given
        when(userPersistencePort.findById(1L)).thenReturn(userModel);

        //when
        var socio = userUseCase.findById(1L);

        //then
        assertNotNull(socio);
        assertEquals(userModel.getDni(), socio.getDni());
        assertEquals(userModel.getEmail(), socio.getEmail());
        assertEquals(userModel.getPassword(), socio.getPassword());
        verify(userPersistencePort, times(1)).findById(1L);
    }

    @Test
    void findById_notFound() {
        //given
        when(userPersistencePort.findById(1L)).thenReturn(null);

        //when
        assertThrows(DataNotFoundException.class, () -> userUseCase.findById(1L));

        //then
        verify(userPersistencePort, times(1)).findById(1L);
    }
}