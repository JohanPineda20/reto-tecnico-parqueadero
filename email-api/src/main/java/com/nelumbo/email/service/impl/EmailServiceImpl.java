package com.nelumbo.email.service.impl;

import com.nelumbo.email.dto.Email;
import com.nelumbo.email.service.IEmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
@Slf4j
@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements IEmailService {

    private final JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    private String from;
    @Override
    @Async("asyncExecutor")
    public void sendEmail(Email email) {
        try {
            String subject = email.getSubject();
            List<String> to = email.getTo();
            SimpleMailMessage message = new SimpleMailMessage();
            message.setSubject(subject);
            message.setFrom(from);
            message.setText(email.getMessage());
            if (to != null) {
                String[] toAddresses = to.toArray(new String[0]);
                message.setTo(toAddresses);
            }
            javaMailSender.send(message);
            log.info("email sent successfully");
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
