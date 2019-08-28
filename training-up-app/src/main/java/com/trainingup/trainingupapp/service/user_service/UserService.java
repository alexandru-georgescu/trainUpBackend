package com.trainingup.trainingupapp.service.user_service;

import com.trainingup.trainingupapp.dto.CourseDTO;
import com.trainingup.trainingupapp.dto.UserDTO;

import java.util.List;

public interface UserService {
    List<UserDTO> findAll();
    UserDTO findById(long id);
    UserDTO addUser(UserDTO user);
    void removeUser(long id);

    UserDTO loginService(String username, String password);
    boolean validate(String email, String password);
    void wishToEnroll(UserDTO user, CourseDTO course);
}
