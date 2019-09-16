package com.trainingup.trainingupapp.service.email_service;

import com.trainingup.trainingupapp.repository.EmailRepository;
import com.trainingup.trainingupapp.tables.EmailTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SimpleEmailService implements EmailService {

    @Autowired
    EmailRepository emailRepository;

    @Override
    public EmailTemplate getUser(String email) {
        List<EmailTemplate> users = emailRepository.findAll();

        EmailTemplate user = users.stream()
                .filter(u -> u.getEmail().toLowerCase().equals(email))
                .findFirst()
                .orElse(null);

        return user;
    }

    @Override
    public String info(String email) {
        EmailTemplate user = getUser(email);
        if (user == null) {
            return "Invalid email";
        }

        return null;
    }

    @Override
    public String wish(String email) {
        EmailTemplate user = getUser(email);
        if (user == null) {
            return "Invalid email";
        }

        return null;
    }

    @Override
    public String accept(String email) {
        EmailTemplate user = getUser(email);
        if (user == null) {
            return "Invalid email";
        }

        return null;
    }

    @Override
    public String reject(String email) {
        EmailTemplate user = getUser(email);
        if (user == null) {
            return "Invalid email";
        }

        return null;
    }

    @Override
    public String acceptAll(String email) {
        EmailTemplate user = getUser(email);
        if (user == null) {
            return "Invalid email";
        }

        return null;
    }

    @Override
    public String rejectAll(String email) {
        EmailTemplate user = getUser(email);
        if (user == null) {
            return "Invalid email";
        }

        return null;
    }

    @Override
    public String help(String email) {
        EmailTemplate user = getUser(email);
        if (user == null) {
            return "Invalid email";
        }

        return null;
    }
}
