package com.nelumbo.parking.infraestructure.out.authenticationinfo;

import com.nelumbo.parking.domain.ports.out.IAuthenticationInfoPort;
import com.nelumbo.parking.infraestructure.security.userdetails.UserDetailsImpl;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuthenticationInfoAdapter implements IAuthenticationInfoPort {
    @Override
    public Long getIdFromAuthentication() {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userDetails.getId();
    }
}
