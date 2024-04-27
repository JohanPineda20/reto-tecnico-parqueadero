package com.nelumbo.parking.application.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ParkingRequest {
    @NotBlank(message = "The name cannot be empty")
    private String name;
    @NotNull(message = "The capacity is required")
    @Min(value = 1, message = "The capacity must be a positive number")
    private Long capacity;
    @NotNull(message = "The cost per hour cannot be empty")
    @DecimalMin(value = "0.1", message = "The cost per hour must be a positive number")
    private BigDecimal costPerHour;
    @NotNull(message = "The user_id is required")
    @Min(value = 1, message = "The user_id must be a positive number")
    private Long userId;
}
