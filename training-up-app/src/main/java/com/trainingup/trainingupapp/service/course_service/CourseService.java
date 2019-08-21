package com.trainingup.trainingupapp.service.course_service;

import com.trainingup.trainingupapp.models.CourseModel;
import java.time.LocalDate;
import java.util.List;

public interface CourseService {
    List<CourseModel> findAll();
    CourseModel findById(long id);
    CourseModel addCourse(String courseName, int capacity, int actualCapacity, LocalDate startDate,
                   LocalDate endDate, String projectManager);
    void removeCourse(long id);
}
