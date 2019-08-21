
package com.trainingup.trainingupapp.controller;

import com.trainingup.trainingupapp.models.UserModel;
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
    public List<UserModel> introProject(ModelAndView model) {
        return userService.findAll();
    }

    @GetMapping("/login")
    public UserModel loginPage(@RequestParam("username") String email,
                          @RequestParam("password") String password,
                          ModelAndView model) {

        return userService.loginService(email, password);
    }


    @GetMapping("/register")
    public UserModel registerPage(@RequestParam("email") String email,
                             @RequestParam("firstName") String firstName,
                             @RequestParam("lastName") String lastName,
                             @RequestParam("password") String password,
                             @RequestParam("confPassword") String confPassword,
                             ModelAndView model) {

        return userService.addUser(email, firstName, lastName, password, "user", confPassword);
    }

}