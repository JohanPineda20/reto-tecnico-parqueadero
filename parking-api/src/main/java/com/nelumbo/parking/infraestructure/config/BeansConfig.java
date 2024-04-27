package com.nelumbo.parking.infraestructure.config;


import com.nelumbo.parking.domain.ports.in.IParkingServicePort;
import com.nelumbo.parking.domain.ports.in.IUserServicePort;


import com.nelumbo.parking.domain.ports.out.*;
import com.nelumbo.parking.domain.usecase.ParkingUseCase;
import com.nelumbo.parking.domain.usecase.UserUseCase;
import com.nelumbo.parking.infraestructure.out.authenticationinfo.AuthenticationInfoAdapter;
import com.nelumbo.parking.infraestructure.out.jpa.adapter.ParkingPostgresqlAdapter;
import com.nelumbo.parking.infraestructure.out.jpa.adapter.RolePostgresqlAdapter;
import com.nelumbo.parking.infraestructure.out.jpa.adapter.UserPostgresqlAdapter;
import com.nelumbo.parking.infraestructure.out.jpa.mapper.ParkingEntityMapper;
import com.nelumbo.parking.infraestructure.out.jpa.mapper.RoleEntityMapper;
import com.nelumbo.parking.infraestructure.out.jpa.mapper.UserEntityMapper;
import com.nelumbo.parking.infraestructure.out.jpa.repository.ParkingRepository;
import com.nelumbo.parking.infraestructure.out.jpa.repository.RoleRepository;
import com.nelumbo.parking.infraestructure.out.jpa.repository.UserRepository;
import com.nelumbo.parking.infraestructure.out.passwordencoder.PasswordEncoderAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class BeansConfig {
    @Bean
    public IUserPersistencePort userPersistencePort(UserRepository userRepository,
                                                    UserEntityMapper userEntityMapper){
        return new UserPostgresqlAdapter(userRepository, userEntityMapper);
    }
    @Bean
    public IRolePersistencePort rolePersistencePort(RoleRepository roleRepository,
                                                    RoleEntityMapper roleEntityMapper){
        return new RolePostgresqlAdapter(roleRepository, roleEntityMapper);
    }
    @Bean
    public IParkingPersistencePort parkingPersistencePort(ParkingRepository parkingRepository,
                                                          ParkingEntityMapper parkingEntityMapper){
        return new ParkingPostgresqlAdapter(parkingRepository, parkingEntityMapper);
    }
    @Bean
    public IPasswordEncoderPort passwordEncoderPort(PasswordEncoder passwordEncoder){
        return new PasswordEncoderAdapter(passwordEncoder);
    }
    @Bean
    public IAuthenticationInfoPort authenticationInfoPort(){
        return new AuthenticationInfoAdapter();
    }

    @Bean
    public IUserServicePort userServicePort(IUserPersistencePort userPersistencePort,
                                            IRolePersistencePort rolePersistencePort,
                                            IPasswordEncoderPort passwordEncoderPort){
        return new UserUseCase(userPersistencePort, rolePersistencePort, passwordEncoderPort);
    }
    @Bean
    public IParkingServicePort parkingServicePort(IParkingPersistencePort parkingPersistencePort,
                                                  IUserServicePort userServicePort,
                                                  IAuthenticationInfoPort authenticationInfoPort){
        return new ParkingUseCase(parkingPersistencePort, userServicePort, authenticationInfoPort);
    }
}
