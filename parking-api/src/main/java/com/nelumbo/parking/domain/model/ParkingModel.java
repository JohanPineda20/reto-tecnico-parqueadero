package com.nelumbo.parking.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ParkingModel {

    private Long id;
    private String name;
    private Long capacity;
    private BigDecimal costPerHour;
    private UserModel user;
}
