package com.nelumbo.parking.application.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequest {
    @NotBlank(message = "The name cannot be empty")
    private String name;
    private String lastname;
    @NotBlank(message = "The dni cannot be empty")
    @Pattern(regexp = "\\d+", message = "The dni must contain only numbers")
    @Size(max = 10, message = "The dni must have at least 10 digits")
    private String dni;
    @NotBlank(message = "The phone cannot be empty")
    @Pattern(regexp = "\\d+", message = "The phone must contain only numbers")
    @Size(max = 10, message = "The phone number must have at least 10 digits")
    private String phone;
    @NotBlank(message = "The email cannot be empty")
    @Email(message = "The email is not valid")
    private String email;
    @NotBlank(message = "The password cannot be empty")
    private String password;
}
