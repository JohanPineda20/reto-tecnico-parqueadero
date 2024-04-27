package com.nelumbo.parking.infraestructure.security.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nelumbo.parking.infraestructure.out.jpa.entity.UserEntity;
import com.nelumbo.parking.infraestructure.out.jpa.repository.UserRepository;
import com.nelumbo.parking.infraestructure.security.jwt.JwtProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        CredentialsRequest credentials;
        try {
            credentials = new ObjectMapper().readValue(request.getInputStream(), CredentialsRequest.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                credentials.getEmail(),
                credentials.getPassword());

        return getAuthenticationManager().authenticate(auth);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {

        String token = jwtProvider.createToken(authResult);

        UserEntity userEntity = userRepository.findByEmail(authResult.getName()).orElse(null);
        userEntity.setToken(token);
        userRepository.save(userEntity);

        Map<String, Object> res = new HashMap<>();
        res.put("token", token);

        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(new ObjectMapper().writeValueAsString(res));
        response.getWriter().flush();

        super.successfulAuthentication(request, response, chain, authResult);
    }
}
