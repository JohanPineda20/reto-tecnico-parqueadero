package com.nelumbo.parking.domain.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Constants {
    public static final byte ZERO = 0;
    public static final Long ID_SOCIO = 2L;
    public static final String SOCIO = "SOCIO";
    public static final String ROLE_NOT_FOUND = "Role not found";
    public static final String USER_NOT_FOUND = "User not found";
    public static final String PARKING_NOT_FOUND = "Parking not found";
    public static final String USER_MUST_BE_SOCIO = "User must be a socio";
    public static final String USER_IS_NOT_PARKING_SOCIO= "You are not a socio of the parking";
    public static final String VEHICLE_IS_IN_THE_PARKING = "No se puede Registrar Ingreso, ya existe la placa en este u otro parqueadero";
    public static final String VEHICLE_IS_NOT_THE_PARKING = "No se puede Registrar Salida, no existe la placa en el parqueadero";
    public static final String PARKING_IS_FULL = "The parking is full";
    public static final String DNI = "DNI";
    public static final String EMAIL = "email";
    public static final String USER_ALREADY_EXISTS = "User with %s: %s already exists";
    public static final String PARKING_ALREADY_EXISTS = "There is already another parking with the same name";
}
