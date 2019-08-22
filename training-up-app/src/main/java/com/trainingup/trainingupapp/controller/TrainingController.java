
package com.trainingup.trainingupapp.controller;

import com.trainingup.trainingupapp.dto.CourseDTO;
import com.trainingup.trainingupapp.dto.UserDTO;
import com.trainingup.trainingupapp.service.course_service.CourseService;
import com.trainingup.trainingupapp.service.user_service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@CrossOrigin
public class TrainingController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private UserService userService;

    @GetMapping("/user")
    public String introProject(Model model) {
        List<UserDTO> users  = userService.findAll();
        model.addAttribute("users", users);
        return "findAll";
    }

    @ResponseBody
    @GetMapping("/user/login")
    public UserDTO loginPage(@RequestParam("username") String email,
                             @RequestParam("password") String password,
                             ModelAndView model) {

        return userService.loginService(email, password);
    }

    @ResponseBody
    @PostMapping("/user/register")
    public UserDTO registerPage(@RequestBody UserDTO user) {
        return userService.addUser(user);
    }


}