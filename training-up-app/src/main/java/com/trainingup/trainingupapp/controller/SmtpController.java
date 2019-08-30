package com.trainingup.trainingupapp.controller;

import com.trainingup.trainingupapp.service.smtp_service.SmtpService;
import com.trainingup.trainingupapp.service.user_service.UserService;
import com.trainingup.trainingupapp.tables.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@RestController
@CrossOrigin (origins = "*")
public class SmtpController {

    @Autowired
    UserService userService;

    @GetMapping("/trainup/validate")
    public void validateEmail(@PathVariable(value = "id") String token) {
        List<User> users = userService.findAllDB();
        users.forEach(us -> {
            if (us.getToken().equals(token)) {
                us.setEnable(true);
                userService.saveAndFlush(us);
            }
        });
    }
}
