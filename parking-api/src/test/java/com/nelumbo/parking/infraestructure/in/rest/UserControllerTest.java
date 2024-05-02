package com.nelumbo.parking.infraestructure.in.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nelumbo.parking.application.dto.request.UserRequest;
import com.nelumbo.parking.application.dto.response.UserResponse;
import com.nelumbo.parking.application.handler.IUserHandler;
import com.nelumbo.parking.domain.utils.exceptions.DataNotFoundException;
import com.nelumbo.parking.infraestructure.exceptionhandler.ErrorHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {
    @Mock
    private IUserHandler userHandler;
    @InjectMocks
    private UserController userController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp(){
        mockMvc = MockMvcBuilders.standaloneSetup(userController).setControllerAdvice(new ErrorHandler()).build();
    }

    @Test
    void createSocio() throws Exception {
        //given
        UserRequest userRequest = new UserRequest();
        userRequest.setName("example");
        userRequest.setDni("12345");
        userRequest.setPhone("1234");
        userRequest.setEmail("example@ghmail.com");
        userRequest.setPassword("password");
        UserResponse userResponse = new UserResponse();
        userResponse.setId(1L);
        userResponse.setName("example");
        when(userHandler.createSocio(any(UserRequest.class))).thenReturn(userResponse);

        //when
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.post("/v1/user")
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .content(jsonToString(userRequest)));

        //then
        response.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(userResponse.getId()))
                .andExpect(jsonPath("$.name").value(userResponse.getName()));
    }

    @Test
    void createSocio_notFound() throws Exception {
        //given
        UserRequest userRequest = new UserRequest();
        userRequest.setName("example");
        userRequest.setDni("12345");
        userRequest.setPhone("1234");
        userRequest.setEmail("example@ghmail.com");
        userRequest.setPassword("password");
        when(userHandler.createSocio(any(UserRequest.class))).thenThrow(new DataNotFoundException("Role not found"));

        //when
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.post("/v1/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonToString(userRequest)));

        //then
        response.andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("Role not found"))
                .andExpect(jsonPath("$.statusCode").value(404));
    }

    private String jsonToString(Object o){
        try{
            return new ObjectMapper().writeValueAsString(o);
        }catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}