package com.nelumbo.parking.infraestructure.in.rest;

import com.nelumbo.parking.application.dto.response.MetricVehicleResponse;
import com.nelumbo.parking.application.dto.response.VehicleResponse;
import com.nelumbo.parking.application.handler.IHistorialHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/v1/metric")
@RequiredArgsConstructor
public class MetricController {

    private final IHistorialHandler historialHandler;
    @SecurityRequirement(name = "jwt")
    @Operation(summary = "Get the top 10 most parked vehicles in all the parkings",
            description = "Admin and Socio users are allowed to get the top 10 most parked vehicles in all the parkings")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The top 10 most parked vehicles are successfully returned", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = MetricVehicleResponse.class)))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Exception"))),
            @ApiResponse(responseCode = "401", description = "Access denied", content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Exception"))),
    })
    @GetMapping("/top-10-most-parked-vehicles")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SOCIO')")
    public ResponseEntity<List<MetricVehicleResponse>> getTop10MostParkedVehicles(){
        return ResponseEntity.ok(historialHandler.getTop10MostParkedVehicles());
    }
    @SecurityRequirement(name = "jwt")
    @Operation(summary = "Get the top 10 most parked vehicles in a parking",
            description = "Admin and Socio users are allowed to get the top 10 most parked vehicles in a parking")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The top 10 most parked vehicles in a parking are successfully returned", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = MetricVehicleResponse.class)))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Exception"))),
            @ApiResponse(responseCode = "401", description = "Access denied", content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Exception"))),
            @ApiResponse(responseCode = "404", description = "Parking not found", content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Exception"))),
            @ApiResponse(responseCode = "409", description = "Operation not allowed: User is not a socio of the parking", content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Exception")))
    })
    @GetMapping("/top-10-most-parked-vehicles/parking/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SOCIO')")
    public ResponseEntity<List<MetricVehicleResponse>> getTop10MostParkedVehiclesByParking(@PathVariable Long id){
        return ResponseEntity.ok(historialHandler.getTop10MostParkedVehiclesByParking(id));
    }
    @SecurityRequirement(name = "jwt")
    @Operation(summary = "Get the first-time-parked vehicles in a parking",
            description = "Admin and Socio users are allowed to get the first-time-parked vehicles in a parking")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The first-time-parked vehicles in a parking are successfully returned", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = VehicleResponse.class)))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Exception"))),
            @ApiResponse(responseCode = "401", description = "Access denied", content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Exception"))),
            @ApiResponse(responseCode = "404", description = "Parking not found", content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Exception"))),
            @ApiResponse(responseCode = "409", description = "Operation not allowed: User is not a socio of the parking", content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Exception")))
    })
    @GetMapping("/first-time-parked-vehicles/parking/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SOCIO')")
    public ResponseEntity<List<VehicleResponse>> getFirstTimeParkedVehiclesByParking(@PathVariable Long id){
        return ResponseEntity.ok(historialHandler.getFirstTimeParkedVehiclesByParking(id));
    }
    @SecurityRequirement(name = "jwt")
    @Operation(summary = "Search for vehicles by license plate in all the parkings",
            description = "Admin and Socio users are allowed to search for vehicles by license plate in all the parkings")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The vehicles are successfully returned", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = VehicleResponse.class)))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Exception"))),
            @ApiResponse(responseCode = "401", description = "Access denied", content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Exception")))
    })
    @GetMapping("/vehicle")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SOCIO')")
    public ResponseEntity<List<VehicleResponse>> getVehicleByLicensePlate(@RequestParam String licensePlate){
        return ResponseEntity.ok(historialHandler.getVehicleByLicensePlate(licensePlate));
    }
    @SecurityRequirement(name = "jwt")
    @Operation(summary = "Get the cash incomes of a parking",
            description = "Socio user is allowed to get the cash incomes of a parking")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The cash incomes are successfully returned", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Map.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Exception"))),
            @ApiResponse(responseCode = "401", description = "Access denied", content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Exception"))),
            @ApiResponse(responseCode = "404", description = "Parking not found", content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Exception"))),
            @ApiResponse(responseCode = "409", description = "Operation not allowed: User is not a socio of the parking", content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Exception")))
    })
    @GetMapping("/cash-income/parking/{id}")
    @PreAuthorize("hasAuthority('SOCIO')")
    public ResponseEntity<Map<String, BigDecimal>> getCashIncomeByParking(@PathVariable Long id){
        return ResponseEntity.ok(historialHandler.getCashIncomeByParking(id));
    }
}
