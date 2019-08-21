package com.trainingup.trainingupapp.service.user_service;
import com.trainingup.trainingupapp.models.UserModel;
import com.trainingup.trainingupapp.repository.UserRepository;
import com.trainingup.trainingupapp.tables.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SimpleUserService implements UserService {

    @Autowired
    UserRepository userRepository;

    List<UserModel> userBackend = new ArrayList<>();;

    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<UserModel> findAll() {
        return this.userBackend;
    }

    @Override
    public UserModel findById(long id) {
        return this.userBackend
                .stream()
                .filter(e -> e.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public UserModel addUser(String email, String firstName, String lastName,
                             String password, String type, String confPassword) {

        if (firstName.equals("") || lastName.equals("") || password.equals("")) {
            return null;
        }

        if (!validate(email, password)) {
            return null;
        }
        User checkUser = userRepository
                .findAll()
                .stream()
                .filter(e -> e.getEmail().equals(email))
                .findFirst()
                .orElse(null);

        if (checkUser != null) {
            return null;
        }

        User newUser = new User();

        newUser.setEmail(email);
        newUser.setFirstName(firstName);
        newUser.setLastName(lastName);
        newUser.setPassword(password);
        newUser.setType(type);

        userRepository.saveAndFlush(newUser);

        UserModel newBackendUser = newUser.convertToUserModel();

        userBackend.add(newBackendUser);
        return newBackendUser;
    }

    @Override
    public void removeUser(long id) {
        this.userRepository.deleteById(id);
        UserModel dummy = this.userBackend
                .stream()
                .filter(el -> el.getId() == id)
                .findFirst()
                .orElse(null);

        if (dummy != null) {
            this.userBackend.remove(dummy);
        }
    }

    @Override
    public UserModel loginService(String email, String password) {

        if (!validate(email, password)) {
            return null;
        }

        Optional<UserModel> user = userBackend.stream().filter(u -> {
            if (u.getEmail().toLowerCase().equals(email.toLowerCase()) &&
                    u.getPassword().equals(password)) {
                return true;
            }
            return false;
        }).findFirst();
        return user.orElse(null);
    }

    @Override
    public boolean validate(String email, String password) {
        if (!email.contains("@trainup.com")) {
            return false;
        }

        if(email.equals("") || password.equals("")) {
            return false;
        }

        int atIndex = email.indexOf("@");

        if (email.lastIndexOf("@") != atIndex) {
            return false;
        }

        String beforeAt = email.substring(0, atIndex);

        if (!beforeAt.contains(".")) {
            return false;
        }

        if (!beforeAt.matches(".*[a-zA-Z.]+.")) {
            return false;
        }

        return false;
    }
}
