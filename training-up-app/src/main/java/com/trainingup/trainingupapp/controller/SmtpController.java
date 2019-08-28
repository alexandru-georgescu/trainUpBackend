package com.trainingup.trainingupapp.controller;

import com.trainingup.trainingupapp.service.smtp_service.SmtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SmtpController {

//    @Autowired
//    SmtpService smtpService;
//
//    @ResponseBody
//    @GetMapping("/smtp/emails")
//    public List<String> getAllEmails() {
//        return smtpService.getEmail();
//    }
}
