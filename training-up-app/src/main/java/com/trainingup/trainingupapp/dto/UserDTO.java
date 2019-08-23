package com.trainingup.trainingupapp.dto;

import com.trainingup.trainingupapp.tables.Course;
import com.trainingup.trainingupapp.tables.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@ToString
@Accessors(chain = true)
public class UserDTO {
    private long id;
    private String email;
    private String type;
    private String firstName;
    private String lastName;
    private String password;
    private List<Course> courses;

    @Transient
    public User convertToUser() {
        User userModel = new User();
        if (userModel.getCourses() == null) {
            userModel.setCourses(new ArrayList<Course>());
        }

        userModel.setId(id);
        userModel.setType(type);
        userModel.setPassword(password);
        userModel.setLastName(lastName);
        userModel.setFirstName(firstName);
        userModel.setCourses(courses);
        userModel.setEmail(email);
        return userModel;
    }
}
