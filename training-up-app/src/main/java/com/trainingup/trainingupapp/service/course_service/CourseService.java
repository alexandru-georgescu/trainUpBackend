package com.trainingup.trainingupapp.service.course_service;

import com.trainingup.trainingupapp.tables.Course;

import java.time.LocalDate;
import java.util.List;

public interface CourseService {
    List<Course> findAll();
    Course findById(long id);
    Course addCourse(String courseName, int capacity, int actualCapacity, LocalDate startDate,
                   LocalDate endDate, String projectManager);
    void removeTask(long id);
}
