package com.nelumbo.parking.infraestructure.out.feignclient.dto;

import lombok.Data;

import java.util.List;

@Data
public class MessageRequest {
    private List<String> to;
    private String subject;
    private String message;
    private String licensePlate;
}
