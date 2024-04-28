package com.nelumbo.parking.application.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HistorialRequest {
    @Pattern(regexp = "^[a-zA-Z0-9]{6}$", message = "The license plate must consist of exactly 6 characters. It should contain only alphanumeric characters; special characters and the letter 'ñ' are not permitted.")
    private String licensePlate;
    private String description;
    @NotNull(message = "The parking_id is required")
    @Min(value = 1, message = "The parking_id must be a positive number")
    private Long parkingId;
}
