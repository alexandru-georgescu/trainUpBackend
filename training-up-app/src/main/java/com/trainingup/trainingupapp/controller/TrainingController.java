
package com.trainingup.trainingupapp.controller;

import com.trainingup.trainingupapp.repository.CourseRepository;
import com.trainingup.trainingupapp.repository.UserRepository;
import com.trainingup.trainingupapp.service.course_service.CourseService;
import com.trainingup.trainingupapp.service.user_service.UserService;
import com.trainingup.trainingupapp.tables.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class TrainingController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public List<User> introProject(ModelAndView model) {
        return userService.findAll();
    }

    @GetMapping("/login")
    public User loginPage(@RequestParam("username") String email,
                          @RequestParam("password") String password,
                          ModelAndView model) {

        String emailToLowerCase = email.toLowerCase();

        if (!email.contains("@trainup.com"))
            return null;

        if(email.equals("") || password.equals(""))
            return null;

        Optional<User> user = userService.findAll().stream().filter(u -> {
            if (u.getEmail().equals(email) && u.getPassword().equals(password)) {
                return true;
            }
            return false;
        }).findFirst();
        return user.orElse(null);
    }

    @GetMapping("/register")
    public User registerPage(@RequestParam("email") String email,
                             @RequestParam("firstName") String firstName,
                             @RequestParam("lastName") String lastName,
                             @RequestParam("password") String password,
                             @RequestParam("confPassword") String confPassword,
                             ModelAndView model) {

        if (firstName.equals("") || lastName.equals("") || password.equals("")) {
            return null;
        }

        if (!email.contains("@trainup.com") || !confPassword.equals(password)) {
            return null;
        }

        ///Index of the @ in the string
        int atIndex = email.indexOf("@");


        ///If the email the user has entered has more than just a @ return null
        if (email.lastIndexOf("@") != atIndex)
            return null;

        ///String before the @ in an email
        String beforeAt = email.substring(0, atIndex);

        ///The body of the email before the @ should containt a "."
        if (!beforeAt.contains("."))
            return null;

        ///If the body of the email before the @ contains anything else than letters and the "." return null
        if (!beforeAt.matches(".*[a-zA-Z.]+."))
            return null;

        return userService.addUser(email, firstName, lastName, password, "user");
    }

}