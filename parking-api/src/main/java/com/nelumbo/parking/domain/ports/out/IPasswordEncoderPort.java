package com.nelumbo.parking.domain.ports.out;

public interface IPasswordEncoderPort {
    String encode(String password);
}
