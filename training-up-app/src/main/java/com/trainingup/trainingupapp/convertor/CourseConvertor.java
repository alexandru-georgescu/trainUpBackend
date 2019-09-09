package com.trainingup.trainingupapp.convertor;

import com.trainingup.trainingupapp.dto.CourseDTO;
import com.trainingup.trainingupapp.tables.Course;

import java.time.LocalDate;

public class CourseConvertor {

    public static Course convertToCourse(CourseDTO course) {
        Course newCourse = new Course();
        newCourse.setProjectManager(course.getProjectManager());

        LocalDate start = course.getStartDate();
        start.plusDays(1);

        LocalDate stop = course.getStartDate();
        stop.plusDays(1);

        newCourse.setEndDate(stop);
        newCourse.setStartDate(stop);
        newCourse.setId(course.getId());
        newCourse.setCapacity(course.getCapacity());
        newCourse.setActualCapacity(course.getActualCapacity());
        newCourse.setCourseName(course.getCourseName());
        newCourse.setDomain(course.getDomain());
        return newCourse;
    }

    public static CourseDTO convertToCourseDTO(Course course) {
        CourseDTO newCourse = new CourseDTO();
        newCourse.setProjectManager(course.getProjectManager());
        newCourse.setEndDate(course.getEndDate());
        newCourse.setStartDate(course.getStartDate());
        newCourse.setId(course.getId());
        newCourse.setCapacity(course.getCapacity());
        newCourse.setActualCapacity(course.getActualCapacity());
        newCourse.setCourseName(course.getCourseName());
        newCourse.setDomain(course.getDomain());
        return newCourse;
    }
}
