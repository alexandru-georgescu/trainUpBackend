package com.trainingup.trainingupapp.threads;

import com.trainingup.trainingupapp.dto.CourseDTO;
import com.trainingup.trainingupapp.dto.MailDTO;
import com.trainingup.trainingupapp.dto.UserDTO;
import com.trainingup.trainingupapp.service.course_service.CourseService;
import com.trainingup.trainingupapp.service.user_service.UserService;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.MimeMultipart;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

@Component
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

    @Autowired
    private UserService userService;

    @Autowired
    private CourseService courseService;

    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setCourseService(CourseService courseService) {
        this.courseService = courseService;
    }

    public void sendEmail(String to) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(to);
        message.setSubject("Message Received!");
        message.setText("Hi, \n \n Your request has been received!s");

        javaMailSender.send(message);
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

                    System.out.println("AM PRIMIT MAIL URI BA!");
                    for (int i = 0; i < emails.size(); i++) {
                        if (emails.get(i).getSubject().split("]").length != 4) {
                            //Thread.sleep(15000);
                            continue;
                        }

                        String[] subject = emails.get(i)
                                .getSubject()
                                .replace("[", "")
                                .replace("]", " ")
                                .split(" ");

                        String courseName = subject[0];


                        String body = emails.get(i).getBody();
                        String[] pars = body.split("\n");

                        for (int j = 1; j < pars.length; j++) {
                            pars[i] = pars[i].replace("\r", "");
                        }

                        /**
                         * TODO: REGEX PENTRU MAIL, VERIFICARE MAIL VALID
                         * VLAD FA ASTA DACA NU AI CE FACE!
                         * PS: ASTA SPER SA MEARGA
                         * + = CEL PUTIN O DATA
                         * * = DE 0 SAU MAI MULTE ORI
                         * ESCAPEZI CU / ORICE CARACTER GALBEN DACA VREI CA EL SA FIE INTERPRETAT CA SI TEXT
                         * NU DA PUSH PE MASTER
                         * DAI PULL INAINTE SA TE APUCI DE LUCRU
                         * DACA AVEM CONFLITE ITI RUP CAPU
                         * HAVE FUN!
                         */
                        getUsersFromEmail(pars, courseName);
                        //sendEmail(emails.get(i).getFrom());
                    }
                }

                Thread.sleep(15000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
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
                    Object content = dummy.getContent();
                    if (content instanceof String) {
                        mail.setBody((String) content);
                        System.out.println((String) content);
                        continue;
                    }

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

    public void getUsersFromEmail(String[] body, String courseName) {

        List<UserDTO> serviceUsers = userService.findAll();

        CourseDTO course = courseService
                .findAll().stream()
                .filter(c -> c.getCourseName().toLowerCase().equals(courseName.toLowerCase()))
                .findFirst().orElse(null);

        if (course == null) {
            return;
        }

        Arrays.stream(body).forEach(element -> {
            String newElement = element.replaceAll("\r", "");

            UserDTO user1 = serviceUsers.stream()
                    .filter(user -> user.getEmail().toLowerCase().equals(newElement.toLowerCase()))
                    .findFirst()
                    .orElse(null);

            if (user1 != null) {
                userService.waitToEnroll(user1, course);
            }
        });

        System.out.println("Done!");

    }

}
