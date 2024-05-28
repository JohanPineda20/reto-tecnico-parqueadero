package com.nelumbo.email.controller;

import com.nelumbo.email.dto.Email;
import com.nelumbo.email.persistence.document.EmailCount;
import com.nelumbo.email.persistence.document.TopLicensePlate;
import com.nelumbo.email.service.IEmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/email")
public class EmailController {

    private final IEmailService emailService;

    @PostMapping
    public void sendEmail(@RequestBody Email email) {
        log.info(email.toString());
        emailService.sendEmail(email);
    }

    @GetMapping("/most-frequent")
    public EmailCount getMostFrequentEmail(){
        return emailService.getMostFrequent();
    }
    @GetMapping
    public Map<String, Long> getNumberOfMessagesByDate(@DateTimeFormat(pattern = "yyyy-MM-dd") @RequestParam(required = false) LocalDate date){
        Long count = emailService.getNumberOfMessagesByDate(date);
        return Collections.singletonMap("count", count);
    }
    @GetMapping("/top-10-license-plates")
    public List<TopLicensePlate> getNumberOfMessagesByDate(){
        return emailService.getTop10LicensePlatesByMonth();
    }
}