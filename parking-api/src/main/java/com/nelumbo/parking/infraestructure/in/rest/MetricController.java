package com.nelumbo.parking.infraestructure.in.rest;

import com.nelumbo.parking.application.dto.response.MetricVehicleResponse;
import com.nelumbo.parking.application.handler.IHistorialHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/metric")
@RequiredArgsConstructor
public class MetricController {

    private final IHistorialHandler historialHandler;

    @GetMapping("/top-10-most-parked-vehicles")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SOCIO')")
    public ResponseEntity<List<MetricVehicleResponse>> getTop10MostParkedVehicles(){
        return ResponseEntity.ok(historialHandler.getTop10MostParkedVehicles());
    }

    @GetMapping("/top-10-most-parked-vehicles/parking/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SOCIO')")
    public ResponseEntity<List<MetricVehicleResponse>> getTop10MostParkedVehiclesByParking(@PathVariable Long id){
        return ResponseEntity.ok(historialHandler.getTop10MostParkedVehiclesByParking(id));
    }
}
