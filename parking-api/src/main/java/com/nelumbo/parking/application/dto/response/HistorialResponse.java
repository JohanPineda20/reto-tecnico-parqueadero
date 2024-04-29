package com.nelumbo.parking.application.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class HistorialResponse {
    private VehicleResponse vehicle;
    private LocalDateTime entryDate;
}
