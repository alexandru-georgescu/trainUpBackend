package com.trainingup.trainingupapp.threads;

import com.trainingup.trainingupapp.dto.MailDTO;
import com.trainingup.trainingupapp.dto.UserDTO;
import org.jsoup.Jsoup;

import javax.mail.*;
import javax.mail.internet.MimeMultipart;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class SmtpThread extends Thread {

    private String username = "trainupapply@gmail.com";
    private String password = "trainUp112";
    private String host = "pop.gmail.com";
    private String prot = "pop3";
    private String port = "995";


    private Store store;
    private Properties properties;
    private Session session;
    private Folder folder;

    public void sendEmail() {
    }


    public List<MailDTO> getEmail() {
        try {
            initPop3();
            Message[] messages = folder.getMessages();
            List<MailDTO> messagesArray = new ArrayList<>();
            if (messages.length == 0) {
                return null;
            }

            for (int j = 0; j < messages.length; ++j) {
                //email
                Message dummy = messages[j];
                MailDTO mail = new MailDTO();

                mail.setFrom(dummy.getFrom()[0].toString());
                mail.setSubject(dummy.getSubject());

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

                    mail.setBody(result);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                messagesArray.add(mail);
            }
            store.close();
            return messagesArray;
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        try {
            store.close();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return null;
    }

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
    public void run() {
        while (true) {
            try {
                synchronized (this) {
                    List<MailDTO> emails = getEmail();
                    if (emails == null) {
                        Thread.sleep(15000);
                        continue;
                    }
                    String[] subject = emails.get(0)
                            .getSubject()
                            .split("[[a-zA-Z]]", 0);

                    String courseName = subject[0];
                    System.out.println(subject.toString());
                    System.out.println(courseName);
                }
                Thread.sleep(15000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
