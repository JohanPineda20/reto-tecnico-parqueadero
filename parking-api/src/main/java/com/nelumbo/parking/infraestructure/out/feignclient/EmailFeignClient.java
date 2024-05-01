package com.nelumbo.parking.infraestructure.out.feignclient;

import com.nelumbo.parking.infraestructure.out.feignclient.dto.MessageRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name="email-api", url="${endpoints.email-api}")
public interface EmailFeignClient {
    @PostMapping
    void sendEmail(@RequestBody MessageRequest messageRequest);
}