package com.trainingup.trainingupapp.controller;

import com.trainingup.trainingupapp.dto.PasswordDTO;
import com.trainingup.trainingupapp.dto.UserDTO;
import com.trainingup.trainingupapp.service.user_service.UserService;
import com.trainingup.trainingupapp.tables.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

@Controller
@CrossOrigin(origins = "*")
public class ResetPasswordController {

    @Autowired
    private UserService userService;

    Map<String, String> map = new HashMap<>();
    Queue<Integer> requestQueue = new LinkedList<>();
    int requestOrder = 0;


    @GetMapping("/trainup/reset")
    public String resetPassword(@RequestParam("id") String token, Model model) {
        PasswordDTO pass = new PasswordDTO();

        map.put("token" + requestOrder, token);
        requestQueue.add(requestOrder);
        requestOrder++;

        model.addAttribute("reset_password", pass);
        return "reset";
    }

    @PostMapping("/trainup/reset_password")
    @ResponseBody
    public String greetingSubmit(@ModelAttribute PasswordDTO passwordDTO, Model model) {

        String token = map.get("token" + requestQueue.poll());
        System.out.println("token" + token);

        User user = userService.findAllDB()
                .stream()
                .filter(u -> u.getToken().equals(token))
                .findFirst()
                .orElse(null);

        UserDTO userDTO = userService.findAll()
                .stream()
                .filter(u -> u.getId() == user.getId())
                .findFirst()
                .orElse(null);

        if (!passwordDTO.getPassword().equals(passwordDTO.getConfirm_password())) {
            return "Parola nu corespunde!";
        }

        if (passwordDTO.getPassword().length() < 8) {
            return "Parola prea mica!";
        }

        user.setPassword(passwordDTO.getPassword());
        userDTO.setPassword(passwordDTO.getPassword());

        userService.saveAndFlush(user);

        return "Parola modificata!";
    }


}
