package com.trainingup.trainingupapp.service.course_service;

import com.trainingup.trainingupapp.TrainingUpAppApplication;
import com.trainingup.trainingupapp.models.CourseModel;
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

    List<CourseModel> backendCourses = new ArrayList<>();

    public void setCourseRepository(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    public List<Course> findAll() {
        return this.courseRepository.findAll();
    }

    @Override
    public Course findById(long id) {
        return this.courseRepository
                .findAll()
                .stream()
                .filter(c -> c.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public Course addCourse(String courseName, int capacity, int actualCapacity, LocalDate startDate,
                          LocalDate endDate, String projectManager) {
        Course newCourse = new Course();

        newCourse.setCourseName(courseName);
        newCourse.setCapacity(capacity);
        newCourse.setActualCapacity(actualCapacity);
        newCourse.setStartDate(startDate);
        newCourse.setEndDate(endDate);
        newCourse.setProjectManager(projectManager);

        CourseModel newBackendCourse = new CourseModel();
        newBackendCourse.setCourseName(courseName);
        newBackendCourse.setCapacity(capacity);
        newBackendCourse.setActualCapacity(actualCapacity);
        newBackendCourse.setStartDate(startDate);
        newBackendCourse.setEndDate(endDate);
        newBackendCourse.setProjectManager(projectManager);
        newBackendCourse.setId(newCourse.getId());

        this.courseRepository.saveAndFlush(newCourse);
        this.backendCourses.add(newBackendCourse);
        return newCourse;
    }

    @Override
    public void removeTask(long id) {
        this.courseRepository.deleteById(id);
        CourseModel dummy = this.backendCourses
                .stream()
                .filter(el -> el.getId() == id)
                .findFirst()
                .orElse(null);

        if (dummy != null) {
            this.backendCourses.remove(dummy);
        }
    }
}
