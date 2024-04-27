package com.nelumbo.parking.domain.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Constants {
    public static final Long ID_SOCIO = 2L;
    public static final String ROLE_NOT_FOUND = "Role not found";
    public static final String USER_NOT_FOUND = "User not found";
    public static final String PARKING_NOT_FOUND = "Parking not found";
    public static final String USER_MUST_BE_SOCIO = "User must be a socio";
    public static final String DNI = "DNI";
    public static final String EMAIL = "email";
    public static final String USER_ALREADY_EXISTS = "User with %s: %s already exists";
    public static final String PARKING_ALREADY_EXISTS = "There is already another parking with the same name";
}
