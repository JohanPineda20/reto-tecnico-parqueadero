package com.nelumbo.parking.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class HistorialModel {
    private Long id;
    private LocalDateTime entryDate;
    private LocalDateTime departureDate;
    private BigDecimal payment;
    private ParkingModel parking;
    private VehicleModel vehicle;
}
