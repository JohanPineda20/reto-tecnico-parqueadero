package com.nelumbo.email.service.impl;

import com.nelumbo.email.dto.Email;
import com.nelumbo.email.persistence.document.EmailCount;
import com.nelumbo.email.persistence.document.Message;
import com.nelumbo.email.persistence.document.TopLicensePlate;
import com.nelumbo.email.persistence.repository.MessageRepository;
import com.nelumbo.email.service.IEmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
@Slf4j
@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements IEmailService {

    private final JavaMailSender javaMailSender;
    private final MessageRepository messageRepository;
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
            Message message1 = new Message();
            message1.setEmail(email.getTo().get(0));
            message1.setLicensePlate(email.getLicensePlate());
            message1.setDateSent(LocalDateTime.now());
            messageRepository.save(message1);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public EmailCount getMostFrequent() {
        return messageRepository.findMostFrequentEmail();
    }

    @Override
    public Long getNumberOfMessagesByDate(LocalDate date) {
        LocalDateTime startOfDay;
        LocalDateTime endOfDay;
        if(date != null) {
            startOfDay = date.atStartOfDay();
            endOfDay = date.atTime(LocalTime.MAX);
        } else{
            startOfDay = LocalDate.now().atStartOfDay();
            endOfDay = LocalDate.now().atTime(LocalTime.MAX);
        }
        Long count = messageRepository.countMessagesByDate(startOfDay, endOfDay);
        return count != null ? count : 0;
    }

    @Override
    public List<TopLicensePlate> getTop10LicensePlatesByMonth() {
        LocalDateTime now = LocalDateTime.now();
        int month = now.getMonthValue();
        int year = now.getYear();
        return messageRepository.findTop10LicensePlatesByMonthAndYear(month, year);
    }
}
