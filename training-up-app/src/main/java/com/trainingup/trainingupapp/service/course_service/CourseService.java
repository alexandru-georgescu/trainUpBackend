package com.trainingup.trainingupapp.service.course_service;

import com.trainingup.trainingupapp.dto.CourseDTO;
import java.util.List;

public interface CourseService {
    List<CourseDTO> findAll();
    CourseDTO findById(long id);
    CourseDTO addCourse(CourseDTO course);
    void removeCourse(long id);
}
