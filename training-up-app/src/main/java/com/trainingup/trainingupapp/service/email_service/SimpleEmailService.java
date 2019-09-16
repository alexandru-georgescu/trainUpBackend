package com.trainingup.trainingupapp.service.email_service;

import com.trainingup.trainingupapp.repository.EmailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SimpleEmailService implements EmailService {

    @Autowired
    EmailRepository emailRepository;


    @Override
    public String info(String email) {
        return null;
    }

    @Override
    public String wish(String email) {
        return null;
    }

    @Override
    public String accept(String email) {
        return null;
    }

    @Override
    public String reject(String email) {
        return null;
    }

    @Override
    public String acceptAll(String email) {
        return null;
    }

    @Override
    public String rejectAll(String email) {
        return null;
    }

    @Override
    public String help(String email) {
        return null;
    }
}
