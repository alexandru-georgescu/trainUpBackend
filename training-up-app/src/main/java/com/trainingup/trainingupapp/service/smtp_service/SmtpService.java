package com.trainingup.trainingupapp.service.smtp_service;

import java.util.List;

public interface SmtpService {
    void sendValidateEmail (String to, String body);
    void sendEmailTo(String to, String subject, String content);
}
