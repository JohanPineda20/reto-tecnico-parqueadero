package com.nelumbo.email.service;

import com.nelumbo.email.dto.Email;
import com.nelumbo.email.persistence.document.EmailCount;
import com.nelumbo.email.persistence.document.TopLicensePlate;

import java.time.LocalDate;
import java.util.List;

public interface IEmailService {
    void sendEmail(Email email);
    EmailCount getMostFrequent();
    Long getNumberOfMessagesByDate(LocalDate date);
    List<TopLicensePlate> getTop10LicensePlatesByMonth();
}
