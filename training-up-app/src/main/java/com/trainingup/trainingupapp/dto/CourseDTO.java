package com.trainingup.trainingupapp.dto;

import com.trainingup.trainingupapp.tables.Course;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.Transient;
import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@ToString
@Accessors(chain = true)
public class CourseDTO {
    private long id;
    private String courseName;
    private int capacity;
    private int actualCapacity;
    private LocalDate startDate;
    private LocalDate endDate;
    private String projectManager;

    @Transient
    public Course convertToCourse() {
        Course newCourse = new Course();
        newCourse.setProjectManager(projectManager);
        newCourse.setEndDate(endDate);
        newCourse.setStartDate(startDate);
        newCourse.setId(id);
        newCourse.setCapacity(capacity);
        newCourse.setActualCapacity(actualCapacity);
        newCourse.setCourseName(courseName);

        return newCourse;
    }
}
