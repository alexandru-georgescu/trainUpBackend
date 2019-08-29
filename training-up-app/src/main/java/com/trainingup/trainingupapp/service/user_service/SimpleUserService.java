package com.trainingup.trainingupapp.service.user_service;
import com.trainingup.trainingupapp.convertor.CourseConvertor;
import com.trainingup.trainingupapp.convertor.UserConvertor;
import com.trainingup.trainingupapp.dto.CourseDTO;
import com.trainingup.trainingupapp.dto.UserDTO;
import com.trainingup.trainingupapp.repository.UserRepository;
import com.trainingup.trainingupapp.tables.Course;
import com.trainingup.trainingupapp.tables.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SimpleUserService implements UserService {

    @Autowired
    UserRepository userRepository;

    List<UserDTO> userBackend = new ArrayList<>();

    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<UserDTO> findAll() {
        return this.userBackend;
    }

    @Override
    public List<User> findAllDB() {
        return this.userRepository.findAll();
    }



    @Override
    public UserDTO findById(long id) {
        return this.userBackend
                .stream()
                .filter(e -> e.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public UserDTO addUser(UserDTO user) {

        if (user.getType() == null) {
            user.setType("USER");
        }

        if (!validate(user.getEmail(), user.getPassword())) {
            return null;
        }
        user.setLeader("t.m@trainup.com");
        UserDTO checkUser = userBackend
                .stream()
                .filter(e -> e.getEmail().equals(user.getEmail()))
                .findFirst()
                .orElse(null);

        if (checkUser != null) {
            return null;
        }

        User newUser = UserConvertor.convertToUser(user);
        userRepository.saveAndFlush(newUser);
        user.setId(newUser.getId());

        userBackend.add(user);
        return user;
    }

    @Override
    public void removeUser(long id) {
        this.userRepository.deleteById(id);
        UserDTO dummy = this.userBackend
                .stream()
                .filter(el -> el.getId() == id)
                .findFirst()
                .orElse(null);

        if (dummy != null) {
            this.userBackend.remove(dummy);
        }
    }

    @Override
    public UserDTO loginService(String email, String password) {

        if (!validate(email, password)) {
            return null;
        }

        Optional<UserDTO> user = userBackend.stream().filter(u -> {
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

        if (!beforeAt.matches("[a-zA-Z]*"+ "." + "[a-zA-Z]*")) {
            return false;
        }

        return true;
    }

    @Override
    public UserDTO wishToEnroll(UserDTO userDTO, CourseDTO courseDTO) {
        User userDB = userRepository.findAll()
                .stream().filter(us -> us.getId() == userDTO.getId())
                .findFirst().orElse(null);

        Course courseDB = CourseConvertor.convertToCourse(courseDTO);

        UserDTO userDTO1 = userBackend
                .stream().filter(us -> us.getId() == userDTO.getId())
                .findFirst().orElse(null);

        if (userDB == null || userDTO1 == null) {
            return null;
        }

        List<Course> courseList = userDB.getWishToEnroll();

        Course find = courseList.stream().filter(course -> course.getId() == courseDB.getId())
                .findFirst().orElse(null);


        if (find != null) {
            return userDTO;
        }

        List<Course> userCourse = userDB.getWishToEnroll();
        userCourse.add(courseDB);
        userDB.setWishToEnroll(userCourse);


        List<CourseDTO> userCourseDTO = userDTO1.getWishToEnroll();
        userCourseDTO.add(courseDTO);
        userDTO1.setWishToEnroll(userCourseDTO);

        userRepository.saveAndFlush(userDB);
        return userDTO1;
    }

    @Override
    public List<UserDTO> findAllWithLeader(String leader) {
        return userBackend.stream()
                .filter(user -> user.getLeader().toLowerCase().equals(leader.toLowerCase()))
                .collect(Collectors.toList());
    }

}
