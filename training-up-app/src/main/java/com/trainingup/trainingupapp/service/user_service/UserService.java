package com.trainingup.trainingupapp.service.user_service;

import com.trainingup.trainingupapp.tables.Course;
import com.trainingup.trainingupapp.tables.User;

import java.util.List;

public interface UserService {
    List<User> findAll();
    User findById(long id);
    User addUser(String email, String firstName, String lastName, String password, String type);
    void removeTask(long id);
}
