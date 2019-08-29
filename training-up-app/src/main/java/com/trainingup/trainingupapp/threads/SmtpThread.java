package com.trainingup.trainingupapp.threads;

import com.trainingup.trainingupapp.dto.CourseDTO;
import com.trainingup.trainingupapp.dto.MailDTO;
import com.trainingup.trainingupapp.dto.UserDTO;
import com.trainingup.trainingupapp.service.course_service.CourseService;
import com.trainingup.trainingupapp.service.course_service.SimpleCourseService;
import com.trainingup.trainingupapp.service.user_service.SimpleUserService;
import com.trainingup.trainingupapp.service.user_service.UserService;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.MimeMultipart;
import java.io.IOException;
import java.util.ArrayList;
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


    JavaMailSender emailSender = getJavaMailSender();

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setCourseService(CourseService courseService) {
        this.courseService = courseService;
    }


    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);

        mailSender.setUsername("trainupapply@gmail.com");
        mailSender.setPassword("trainUp112");

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        return mailSender;
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

                    System.out.println(emails.toString());

                    String[] subject = emails.get(0)
                            .getSubject()
                            .replace("[", "")
                            .replace("]", " ")
                            .split(" ");

                    String courseName = subject[0];
                    for (int i = 0; i < subject.length; i++) {
                        System.out.println(subject[i]);
                    }

                    String body = emails.get(0).getBody();

                    String[] pars = body.split("\n");

                    System.out.println(courseName);
                    for (int i = 0; i < pars.length; i++) {
                        System.out.println(pars[i]);
                    }
                    sendEmail(emails.get(0).getFrom(), "Alex", "Love you bobita");
                    //getUsersFromEmail(pars, courseName);
                }

                Thread.sleep(15000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendEmail(String To, String Subject, String Text){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(To);
        message.setSubject(Subject);
        message.setText(Text);
        emailSender.send(message);
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

    public void getUsersFromEmail(String[] body, String courseName) {

        System.out.println(userService);
        List<UserDTO> emailUsers = new ArrayList<>();
        List<UserDTO> serviceUsers = userService.findAll();

        System.out.println(userService.findAll());
        System.out.println(courseService.findAll());

        CourseDTO course = courseService
                .findAll().stream().filter(c -> c.getCourseName().toLowerCase().equals(courseName.toLowerCase()))
                .findFirst().orElse(null);

        if (course == null) {
            return;
        }

        for (int i = 0; i < body.length; i++) {
            String dummy = body[i];
            emailUsers.add(serviceUsers.stream().filter(user -> user.getEmail().equals(dummy)).findFirst().orElse(null));

        }

        emailUsers.forEach(us -> {
            List<CourseDTO> allCourses = us.getCourses();
            allCourses.add(course);
            us.setCourses(allCourses);
        });

    }

}
