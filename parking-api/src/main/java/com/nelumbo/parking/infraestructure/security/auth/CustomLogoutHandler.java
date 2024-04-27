package com.nelumbo.parking.infraestructure.security.auth;

import com.nelumbo.parking.infraestructure.out.jpa.entity.UserEntity;
import com.nelumbo.parking.infraestructure.out.jpa.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomLogoutHandler implements LogoutHandler {
    private final UserRepository userRepository;
    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String authToken = request.getHeader("Authorization");
        if (authToken != null && authToken.startsWith("Bearer ")) {
            String token = authToken.substring(7);
            UserEntity user = userRepository.findByToken(token);
            if (user != null) {
                user.setToken(null);
                userRepository.save(user);
                SecurityContextHolder.clearContext();
            }
        }
    }
}
