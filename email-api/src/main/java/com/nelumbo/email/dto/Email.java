package com.nelumbo.email.dto;

import lombok.Data;

import java.util.List;
@Data
public class Email {
    private List<String> to;
    private String subject;
    private String message;
    private String licensePlate;
}
