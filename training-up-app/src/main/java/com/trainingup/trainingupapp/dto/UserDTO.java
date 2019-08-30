package com.trainingup.trainingupapp.dto;

import com.trainingup.trainingupapp.tables.Course;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

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
    private List<CourseDTO> courses = new ArrayList<>();
    private String leader;
    private List<CourseDTO> wishToEnroll = new ArrayList<>();
    private boolean enable;
}
