package com.nelumbo.parking.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserModel {
    private Long id;
    private String name;
    private String lastname;
    private String dni;
    private String phone;
    private String email;
    private String password;
    private String token;
    private RoleModel role;
}
