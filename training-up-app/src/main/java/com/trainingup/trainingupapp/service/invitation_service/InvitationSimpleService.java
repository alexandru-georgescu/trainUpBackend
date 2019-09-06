package com.trainingup.trainingupapp.service.invitation_service;

import org.springframework.stereotype.Service;

import javax.activation.DataHandler;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import java.util.Properties;

@Service
public class InvitationSimpleService implements InvitationService {


    @Override
    public void send() throws Exception {

        try {
            final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";

            String from = "trainupApply@gmail.com";
            String to = "madalina.lohan@db.com";
            Properties props = new Properties();
            props.setProperty("mail.smtp.host", "smtp.gmail.com");
            props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
            props.setProperty("mail.smtp.socketFactory.fallback", "false");
            props.setProperty("mail.smtp.port", "465");
            props.setProperty("mail.smtp.socketFactory.port", "465");
            props.put("mail.smtp.auth", "true");
            props.put("mail.debug", "true");
            props.put("mail.store.protocol", "pop3");
            props.put("mail.transport.protocol", "smtp");
            final String username = "trainupApply@gmail.com";//
            final String password = "trainUp112";
            props.put("mail.smtp.host", "smtp.gmail.com");

            try {
                Session session = Session.getInstance(props,
                        new Authenticator() {
                            protected PasswordAuthentication getPasswordAuthentication() {
                                return new PasswordAuthentication(username, password);
                            }
                        });
                // Define message
                MimeMessage message = new MimeMessage(session);
                message.addHeaderLine("method=REQUEST");
                message.addHeaderLine("charset=UTF-8");
                message.addHeaderLine("component=VEVENT");

                message.setFrom(new InternetAddress(from));
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
                message.setSubject("Outlook Meeting Request Using JavaMail");

                StringBuffer sb = new StringBuffer();

                StringBuffer buffer = sb.append("BEGIN:VCALENDAR\n" +
                        "PRODID:-//Microsoft Corporation//Outlook 9.0 MIMEDIR//EN\n" +
                        "VERSION:2.0\n" +
                        "METHOD:REQUEST\n" +
                        "BEGIN:VEVENT\n" +
                        "ATTENDEE;ROLE=REQ-PARTICIPANT;RSVP=TRUE:MAILTO:vladaron123@gmail.com\n" +
                        "ORGANIZER:MAILTO:trainupApply@gmail.com\n" +
                        "DTSTART:20051208T053000Z\n" +
                        "DTEND:20051208T060000Z\n" +
                        "LOCATION:Conference room\n" +
                        "TRANSP:OPAQUE\n" +
                        "SEQUENCE:0\n" +
                        "UID:040000008200E00074C5B7101A82E00800000000002FF466CE3AC5010000000000000000100\n" +
                        " 000004377FE5C37984842BF9440448399EB02\n" +
                        "DTSTAMP:20051206T120102Z\n" +
                        "CATEGORIES:Meeting\n" +
                        "DESCRIPTION:Ma rog la bunul Dumnezeu sa mearga acest lucru.\n\n" +
                        "SUMMARY:Ma rog\n" +
                        "PRIORITY:5\n" +
                        "CLASS:PUBLIC\n" +
                        "BEGIN:VALARM\n" +
                        "TRIGGER:PT1440M\n" +
                        "ACTION:DISPLAY\n" +
                        "DESCRIPTION:Reminder\n" +
                        "END:VALARM\n" +
                        "END:VEVENT\n" +
                        "END:VCALENDAR");

                // Create the message part
                BodyPart messageBodyPart = new MimeBodyPart();

                // Fill the message
                messageBodyPart.setHeader("Content-Class", "urn:content-  classes:calendarmessage");
                messageBodyPart.setHeader("Content-ID", "calendar_message");
                messageBodyPart.setDataHandler(new DataHandler(
                        new ByteArrayDataSource(buffer.toString(), "text/calendar")));// very important

                // Create a Multipart
                Multipart multipart = new MimeMultipart();

                // Add part one
                multipart.addBodyPart(messageBodyPart);

                // Put parts in message
                message.setContent(multipart);

                // send message
                Transport.send(message);
            } catch (MessagingException me) {
                me.printStackTrace();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
