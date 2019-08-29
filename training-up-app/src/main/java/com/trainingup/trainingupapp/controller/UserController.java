
package com.trainingup.trainingupapp.controller;

import com.trainingup.trainingupapp.dto.CourseDTO;
import com.trainingup.trainingupapp.dto.CourseUserDTO;
import com.trainingup.trainingupapp.dto.UserDTO;
import com.trainingup.trainingupapp.service.smtp_service.SmtpService;
import com.trainingup.trainingupapp.service.user_service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private SmtpService smtpService;

    @GetMapping("/user")
    public List<UserDTO> introProject() {
        return userService.findAll();
    }

    @GetMapping("/in")
    public List<UserDTO> in() {
        UserDTO pm = new UserDTO();
        pm.setType("PM");
        pm.setLeader("ADMIN");
        pm.setEmail("p.m@trainup.com");
        pm.setFirstName("p");
        pm.setLastName("m");
        pm.setPassword("Pm123456");

        UserDTO tm = new UserDTO();
        tm.setType("TM");
        tm.setLeader("ADMIN");
        tm.setEmail("t.m@trainup.com");
        tm.setFirstName("t");
        tm.setLastName("m");
        tm.setPassword("Tm123456");
        List<CourseDTO> c = new ArrayList<>();
        c.add(new CourseDTO());
        tm.setCourses(c);
        UserDTO user = new UserDTO();
        user.setType("USER");
        user.setLeader("ADMIN");
        user.setEmail("u.s@trainup.com");
        user.setFirstName("u");
        user.setLastName("s");
        user.setPassword("User123456");

        userService.addUser(user);
        userService.addUser(pm);
        userService.addUser(tm);

        return userService.findAll();
    }

    @PostMapping("/user/login")
    public UserDTO loginPage(@RequestBody UserDTO user) {
        return userService.loginService(user.getEmail(), user.getPassword());
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/user/wish")
    public UserDTO wishToEnroll(@RequestBody CourseUserDTO array) {
        return userService.wishToEnroll(array.getUser(), array.getCourse());
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/user/register")
    public UserDTO registerPage(@RequestBody UserDTO user) {
        return userService.addUser(user);
    }

    @GetMapping("/user/remove")
    public void removeUserByIdPage(@RequestParam("id") long id) {
        userService.removeUser(id);
    }

    @GetMapping("/user/find")
    public UserDTO findUserByIdPage(@RequestParam("id") long id) {
        return userService.findById(id);
    }

    @GetMapping("/user/findByLeader")
    public List<UserDTO> findByLeader(@RequestParam("leader") String leader) {
        return userService.findAllWithLeader(leader);
    }
    /**
     *  From smtp controller
     */
    @GetMapping("/smtp/emails")
    public List<String> getAllEmails() {
        return smtpService.getEmail();
    }

}