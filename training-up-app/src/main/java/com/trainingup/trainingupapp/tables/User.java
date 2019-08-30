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
    private String token;
    private boolean enable;
    private String leader;

    @ManyToMany
    private List<Course> courses = new ArrayList<>();

    @ManyToMany
    private List<Course> wishToEnroll = new ArrayList<>();
}
