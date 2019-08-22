package com.trainingup.trainingupapp.tables;

import com.trainingup.trainingupapp.dto.UserDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@ToString
@Accessors(chain = true)
@Entity
@EnableAutoConfiguration
@Table(name="USER")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String email;
    private String type;
    private String firstName;
    private String lastName;
    private String password;

    @OneToMany
    private List<Course> courses = new ArrayList<>();

    @Transient
    public UserDTO convertToUserModel() {
        UserDTO userModel = new UserDTO();
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
