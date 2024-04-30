package com.nelumbo.parking.infraestructure.in.rest;

import com.nelumbo.parking.application.dto.request.HistorialRequest;
import com.nelumbo.parking.application.handler.IHistorialHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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

    @SecurityRequirement(name = "jwt")
    @Operation(summary = "Register entry of a vehicle into a parking",
            description = "Socio user is allowed to register entry of vehicles into a parking")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Vehicle entry successfully registered", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Map.class))),
            @ApiResponse(responseCode = "400", description = "HistorialRequest bad request", content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Exception"))),
            @ApiResponse(responseCode = "401", description = "Access denied", content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Exception"))),
            @ApiResponse(responseCode = "404", description = "Parking not found", content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Exception"))),
            @ApiResponse(responseCode = "409", description = "Operation not allowed: User is not a socio of the parking, the vehicle is already in the parking, the parking is full", content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Exception")))
    })
    @PostMapping("/vehicle-entry")
    @PreAuthorize("hasAuthority('SOCIO')")
    public ResponseEntity<Map<String, Long>> registerVehicleEntry(@Valid @RequestBody HistorialRequest vehicleEntryRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(historialHandler.registerVehicleEntry(vehicleEntryRequest));
    }
    @SecurityRequirement(name = "jwt")
    @Operation(summary = "Register departure of a vehicle from a parking",
            description = "Socio user is allowed to register departure of vehicles from a parking")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The vehicle left the parking successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Map.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Exception"))),
            @ApiResponse(responseCode = "401", description = "Access denied", content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Exception"))),
            @ApiResponse(responseCode = "404", description = "Parking not found", content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Exception"))),
            @ApiResponse(responseCode = "409", description = "Operation not allowed: User is not a socio of the parking, the vehicle is not in the parking", content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Exception")))
    })
    @PostMapping("/vehicle-departure")
    @PreAuthorize("hasAuthority('SOCIO')")
    public ResponseEntity<Map<String, String>> registerVehicleDeparture(@Valid @RequestBody HistorialRequest vehicleDepartureRequest){
        return ResponseEntity.ok(historialHandler.registerVehicleDeparture(vehicleDepartureRequest));
    }
}
