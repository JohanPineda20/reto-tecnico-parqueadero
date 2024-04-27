package com.nelumbo.parking.infraestructure.config;


import com.nelumbo.parking.domain.ports.in.IUserServicePort;


import com.nelumbo.parking.domain.ports.out.IPasswordEncoderPort;
import com.nelumbo.parking.domain.ports.out.IRolePersistencePort;
import com.nelumbo.parking.domain.ports.out.IUserPersistencePort;
import com.nelumbo.parking.domain.usecase.UserUseCase;
import com.nelumbo.parking.infraestructure.out.jpa.adapter.RolePostgresqlAdapter;
import com.nelumbo.parking.infraestructure.out.jpa.adapter.UserPostgresqlAdapter;
import com.nelumbo.parking.infraestructure.out.jpa.mapper.RoleEntityMapper;
import com.nelumbo.parking.infraestructure.out.jpa.mapper.UserEntityMapper;
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
    public IPasswordEncoderPort passwordEncoderPort(PasswordEncoder passwordEncoder){
        return new PasswordEncoderAdapter(passwordEncoder);
    }

    @Bean
    public IUserServicePort userServicePort(IUserPersistencePort userPersistencePort,
                                            IRolePersistencePort rolePersistencePort,
                                            IPasswordEncoderPort passwordEncoderPort){
        return new UserUseCase(userPersistencePort, rolePersistencePort, passwordEncoderPort);
    }
}
