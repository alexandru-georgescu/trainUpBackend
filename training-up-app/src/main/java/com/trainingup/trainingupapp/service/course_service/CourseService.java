package com.trainingup.trainingupapp.service.course_service;

import com.trainingup.trainingupapp.dto.CourseDTO;
import java.time.LocalDate;
import java.util.List;

public interface CourseService {
    List<CourseDTO> findAll();
    CourseDTO findById(long id);
    CourseDTO addCourse(String courseName, int capacity, int actualCapacity, LocalDate startDate,
                        LocalDate endDate, String projectManager);
    void removeCourse(long id);
}
