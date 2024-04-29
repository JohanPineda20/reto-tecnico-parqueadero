package com.nelumbo.parking.application.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MetricVehicleResponse {
    private VehicleResponse vehicle;
    private Long count;
}
