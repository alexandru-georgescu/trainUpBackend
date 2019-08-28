package com.trainingup.trainingupapp.service.smtp_service;

import java.util.List;

public interface SmtpService {
    void sendEmail();
    List<String> getEmail();
    void initPop3();
}
