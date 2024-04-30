package com.nelumbo.email.controller;

import com.nelumbo.email.dto.Email;
import com.nelumbo.email.service.IEmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/email")
public class EmailController {

    private final IEmailService emailService;

    @PostMapping
    public void sendEmail(@RequestBody Email email) {
        emailService.sendEmail(email);
    }

}