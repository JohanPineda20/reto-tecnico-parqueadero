package com.nelumbo.parking.infraestructure.in.rest;

import com.nelumbo.parking.application.dto.request.HistorialRequest;
import com.nelumbo.parking.application.handler.IHistorialHandler;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/v1/historial")
@RequiredArgsConstructor
public class HistorialController {

    private final IHistorialHandler historialHandler;
    @PostMapping("/vehicle-entry")
    @PreAuthorize("hasAuthority('SOCIO')")
    public ResponseEntity<Map<String, Long>> registerVehicleEntry(@Valid @RequestBody HistorialRequest vehicleEntryRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(historialHandler.registerVehicleEntry(vehicleEntryRequest));
    }
    @PostMapping("/vehicle-departure")
    @PreAuthorize("hasAuthority('SOCIO')")
    public ResponseEntity<Map<String, String>> registerVehicleDeparture(@Valid @RequestBody HistorialRequest vehicleDepartureRequest){
        return ResponseEntity.ok(historialHandler.registerVehicleDeparture(vehicleDepartureRequest));
    }
}
