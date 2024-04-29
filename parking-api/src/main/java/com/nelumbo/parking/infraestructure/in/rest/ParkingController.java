package com.nelumbo.parking.infraestructure.in.rest;

import com.nelumbo.parking.application.dto.request.ParkingRequest;
import com.nelumbo.parking.application.dto.response.HistorialResponse;
import com.nelumbo.parking.application.dto.response.ParkingResponse;
import com.nelumbo.parking.application.handler.IParkingHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
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
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/v1/parking")
@RequiredArgsConstructor
public class ParkingController {

    private final IParkingHandler parkingHandler;
    @SecurityRequirement(name = "jwt")
    @Operation(summary = "Add a new parking",
            description = "Admin user is allowed to create a new parking")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Parking created", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ParkingResponse.class))),
            @ApiResponse(responseCode = "400", description = "ParkingRequest bad request", content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Exception"))),
            @ApiResponse(responseCode = "401", description = "Access denied", content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Exception"))),
            @ApiResponse(responseCode = "404", description = "Socio user not found", content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Exception"))),
            @ApiResponse(responseCode = "409", description = "Operation not allowed: Parking already exists, User must be a socio", content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Exception")))
    })
    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ParkingResponse> createParking(@Valid @RequestBody ParkingRequest parkingRequest) {
        ParkingResponse parkingResponse = parkingHandler.createParking(parkingRequest);
        return ResponseEntity.created(
                URI.create(String.format("/api/v1/parking/%s", parkingResponse.getId())))
                .body(parkingResponse);
    }
    @SecurityRequirement(name = "jwt")
    @Operation(summary = "Update a parking",
            description = "Admin user is allowed to update a parking")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Parking updated", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ParkingResponse.class))),
            @ApiResponse(responseCode = "400", description = "ParkingRequest bad request", content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Exception"))),
            @ApiResponse(responseCode = "401", description = "Access denied", content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Exception"))),
            @ApiResponse(responseCode = "404", description = "Socio user not found", content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Exception"))),
            @ApiResponse(responseCode = "409", description = "Operation not allowed: Parking already exists, User must be a socio", content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Exception")))
    })
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ParkingResponse> updateParkingById(@Valid @RequestBody ParkingRequest parkingRequest,
                                                                    @PathVariable Long id){
        return ResponseEntity.ok(parkingHandler.updateParkingById(parkingRequest, id));
    }
    @SecurityRequirement(name = "jwt")
    @Operation(summary = "Delete a parking",
            description = "Admin user is allowed to delete a parking")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Parking deleted", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ParkingResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Exception"))),
            @ApiResponse(responseCode = "401", description = "Access denied", content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Exception"))),
            @ApiResponse(responseCode = "404", description = "Parking not found", content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Exception")))
    })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> deleteParkingById(@PathVariable Long id){
        parkingHandler.deleteParkingById(id);
        return ResponseEntity.noContent().build();
    }
    @SecurityRequirement(name = "jwt")
    @Operation(summary = "Get a parking",
            description = "Admin user is allowed to get a parking by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Parking is successfully returned", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ParkingResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Exception"))),
            @ApiResponse(responseCode = "401", description = "Access denied", content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Exception"))),
            @ApiResponse(responseCode = "404", description = "Parking not found", content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Exception")))
    })
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ParkingResponse> getParkingById(@PathVariable Long id){
        return new ResponseEntity<>(parkingHandler.getParkingById(id), HttpStatus.OK);
    }
    @SecurityRequirement(name = "jwt")
    @Operation(summary = "Get all parkings from the database",
            description = "The admin user will get all the parkings from the database while the socio user will only get its parkings")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Parkings are successfully returned", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ParkingResponse.class)))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Exception"))),
            @ApiResponse(responseCode = "401", description = "Access denied", content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Exception")))
    })
    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SOCIO')")
    public ResponseEntity<List<ParkingResponse>> getAllParkings(@RequestParam(defaultValue = "0") Integer page,
                                                                @RequestParam(defaultValue = "10") Integer size){
        return ResponseEntity.status(HttpStatus.OK).body(parkingHandler.getAllParkings(page, size));
    }
    @GetMapping("/{id}/vehicle")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SOCIO')")
    public ResponseEntity<List<HistorialResponse>> getAllVehiclesInParking(@RequestParam(defaultValue = "0") Integer page,
                                                                           @RequestParam(defaultValue = "10") Integer size,
                                                                           @PathVariable Long id){
        return ResponseEntity.ok(parkingHandler.getAllVehiclesInParking(page, size, id));
    }
}
