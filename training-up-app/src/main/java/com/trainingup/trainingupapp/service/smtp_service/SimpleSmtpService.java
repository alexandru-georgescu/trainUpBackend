package com.trainingup.trainingupapp.service.smtp_service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class SimpleSmtpService implements SmtpService {

   @Autowired
   private JavaMailSender sender;

   public void sendEmailTo(String to, String subject, String content) {
       new Thread(() -> {
           synchronized (this) {
               SimpleMailMessage message = new SimpleMailMessage();
               message.setTo(to);
               message.setSubject(subject);
               message.setText(content);
               sender.send(message);
               System.out.println("done");
           }
       }).start();
   }

   public void sendValidateEmail (String to, String token) {

       new Thread(() -> {
           synchronized (this) {
               SimpleMailMessage message = new SimpleMailMessage();
               message.setTo(to);
               message.setSubject("Account Confirmation");

               message.setText("Hello \n \n"
                       + "Thank you for registring on our site.\n"
                       + "To activate your account please click the link below: \n \n"
                       + "http://127.0.0.1:8080/trainup/validate?id=" + token);
               sender.send(message);
           }
       }).start();
   }
}
