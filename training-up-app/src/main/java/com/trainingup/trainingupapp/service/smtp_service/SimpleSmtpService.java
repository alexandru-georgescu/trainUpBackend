package com.trainingup.trainingupapp.service.smtp_service;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.MimeMultipart;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
@Service
public class SimpleSmtpService implements SmtpService {

    @Autowired
    private JavaMailSender javaMailSender;

    private String username = "trainupapply@gmail.com";
    private String password = "trainUp112";
    private String host = "pop.gmail.com";
    private String prot = "pop3";
    private String port = "995";


    private Store store;
    private Properties properties;
    private Session session;
    private Folder folder;

    @Override
    public void sendEmail() {
    }

    @Override
    public List<String> getEmail() {
        try {
            initPop3();
            Message[] messages = folder.getMessages();
            List<String> messagesArray = new ArrayList<>();

            for (int j = 0; j < messages.length; ++j) {
                //email
                Message dummy = messages[j];

                String message = dummy.getFrom()[0].toString();
                //title
                message += "---" + dummy.getSubject();
                try {
                    String result = "";

                    MimeMultipart mimeMultipart = (MimeMultipart) dummy.getContent();
                    int count = mimeMultipart.getCount();
                    for (int i = 0; i < count; ++i) {

                        BodyPart bodyPart = mimeMultipart.getBodyPart(i);

                        if (bodyPart.isMimeType("text/plain")) {
                            result += '\n' + "" + bodyPart.getContent();
                            break;
                        } else if (bodyPart.isMimeType("text/html")) {
                            String html = (String) bodyPart.getContent();
                            result += '\n' + Jsoup.parse(html).text();
                        }
                    }

                    message += "---" + result;
                } catch (IOException e) {
                    e.printStackTrace();
                }
                messagesArray.add(message);
            }
            return messagesArray;
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void initPop3() {
        if (properties == null) {
            properties = new Properties();
            properties.put("mail.pop3.host", host);
            properties.put("mail.pop3.port", port);
            properties.put("mail.pop3.starttls.enable", "true");
        }

        try {
            session = Session.getDefaultInstance(properties);
            store = session.getStore("pop3s");
            store.connect(host, username, password);
            folder = store.getFolder("INBOX");
            folder.open(Folder.READ_ONLY);
        } catch (MessagingException e) {
            e.printStackTrace();
        }


    }
}
