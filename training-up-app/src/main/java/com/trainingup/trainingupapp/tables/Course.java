package com.trainingup.trainingupapp.tables;

import com.trainingup.trainingupapp.models.CourseModel;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name="COURSES")
@EnableAutoConfiguration
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String courseName;
    private int capacity;
    private int actualCapacity;
    private LocalDate startDate;
    private LocalDate endDate;
    private String projectManager;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getActualCapacity() {
        return actualCapacity;
    }

    public void setActualCapacity(int actualCapacity) {
        this.actualCapacity = actualCapacity;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getProjectManager() {
        return projectManager;
    }

    public void setProjectManager(String projectManager) {
        this.projectManager = projectManager;
    }

    @Transient
    public CourseModel convertToCourseModel() {
        CourseModel newCourse = new CourseModel();
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
