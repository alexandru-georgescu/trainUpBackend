package com.trainingup.trainingupapp.service.course_service;

import com.trainingup.trainingupapp.convertor.CourseConvertor;
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
    public CourseDTO addCourse(CourseDTO course) {
        Course newCourse = CourseConvertor.convertToCourse(course);

        this.courseRepository.saveAndFlush(newCourse);
        course.setId(newCourse.getId());
        this.backendCourses.add(course);
        return course;
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
