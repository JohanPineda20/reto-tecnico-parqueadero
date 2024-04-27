package com.nelumbo.parking.infraestructure.security.userdetails;

import com.nelumbo.parking.infraestructure.out.jpa.entity.UserEntity;
import com.nelumbo.parking.infraestructure.out.jpa.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(username).orElseThrow(()->new UsernameNotFoundException("User with email: "+ username +" not found"));
        return new UserDetailsImpl(userEntity);
    }
}
