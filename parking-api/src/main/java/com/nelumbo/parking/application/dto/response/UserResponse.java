package com.nelumbo.parking.application.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponse {
    private Long id;
    private String name;
    private String lastname;
    private String dni;
    private String phone;
    private String email;
    private String role;
}
