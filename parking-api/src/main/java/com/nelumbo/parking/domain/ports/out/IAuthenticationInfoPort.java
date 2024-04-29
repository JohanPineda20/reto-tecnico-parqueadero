package com.nelumbo.parking.domain.ports.out;

public interface IAuthenticationInfoPort {
    Long getIdFromAuthentication();
    String getRolFromAuthentication();
}
