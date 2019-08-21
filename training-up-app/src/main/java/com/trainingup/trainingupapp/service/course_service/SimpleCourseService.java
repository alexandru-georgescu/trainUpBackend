package com.trainingup.trainingupapp.service.course_service;

import com.trainingup.trainingupapp.dto.CourseDTO;
import com.trainingup.trainingupapp.repository.CourseRepository;
import com.trainingup.trainingupapp.tables.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class SimpleCourseService implements CourseService {

    @Autowired
    CourseRepository courseRepository;

    List<CourseDTO> backendCourses = new ArrayList<>();

    public void setCourseRepository(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    public List<CourseDTO> findAll() {
        return this.backendCourses;
    }

    @Override
    public CourseDTO findById(long id) {
        return this.backendCourses
                .stream()
                .filter(c -> c.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public CourseDTO addCourse(String courseName, int capacity, int actualCapacity, LocalDate startDate,
                               LocalDate endDate, String projectManager) {
        Course newCourse = new Course();

        newCourse.setCourseName(courseName);
        newCourse.setCapacity(capacity);
        newCourse.setActualCapacity(actualCapacity);
        newCourse.setStartDate(startDate);
        newCourse.setEndDate(endDate);
        newCourse.setProjectManager(projectManager);

        CourseDTO newBackendCourse = newCourse.convertToCourseModel();

        this.courseRepository.saveAndFlush(newCourse);
        this.backendCourses.add(newBackendCourse);
        return newBackendCourse;
    }

    @Override
    public void removeCourse(long id) {
        this.courseRepository.deleteById(id);
        CourseDTO dummy = this.backendCourses
                .stream()
                .filter(el -> el.getId() == id)
                .findFirst()
                .orElse(null);

        if (dummy != null) {
            this.backendCourses.remove(dummy);
        }
    }
}
