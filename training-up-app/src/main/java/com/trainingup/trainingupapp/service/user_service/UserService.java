package com.trainingup.trainingupapp.service.user_service;

import com.trainingup.trainingupapp.models.UserModel;
import com.trainingup.trainingupapp.tables.User;

import java.util.List;

public interface UserService {
    List<UserModel> findAll();
    UserModel findById(long id);
    UserModel addUser(String email, String firstName, String lastName, String password,
                      String type, String confPassword);
    void removeUser(long id);

    UserModel loginService(String username, String password);
    boolean validate(String email, String password);
}
