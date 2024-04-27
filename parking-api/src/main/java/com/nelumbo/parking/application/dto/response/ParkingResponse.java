package com.nelumbo.parking.application.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ParkingResponse {
    private Long id;
    private String name;
    private Long capacity;
    private BigDecimal costPerHour;
    private String socioEmail;
}
