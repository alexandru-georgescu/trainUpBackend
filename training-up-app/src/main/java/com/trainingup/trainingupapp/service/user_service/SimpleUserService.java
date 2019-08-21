package com.trainingup.trainingupapp.service.user_service;
import com.trainingup.trainingupapp.models.UserModel;
import com.trainingup.trainingupapp.repository.UserRepository;
import com.trainingup.trainingupapp.tables.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SimpleUserService implements UserService {

    @Autowired
    UserRepository userRepository;

    List<UserModel> userBackend = new ArrayList<>();;

    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> findAll() {
        return this.userRepository.findAll();
    }

    @Override
    public User findById(long id) {
        return this.userRepository
                .findAll()
                .stream()
                .filter(e -> e.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public User addUser(String email, String firstName, String lastName, String password, String type) {
        User newUser = new User();
        newUser.setEmail(email);
        newUser.setFirstName(firstName);
        newUser.setLastName(lastName);
        newUser.setPassword(password);
        newUser.setType(type);

        userRepository.saveAndFlush(newUser);

        UserModel newBackendUser = new UserModel();
        newBackendUser.setEmail(email);
        newBackendUser.setFirstName(firstName);
        newBackendUser.setLastName(lastName);
        newBackendUser.setPassword(password);
        newBackendUser.setId(newUser.getId());
        newBackendUser.setType(type);

        userBackend.add(newBackendUser);
        return newUser;
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
}
