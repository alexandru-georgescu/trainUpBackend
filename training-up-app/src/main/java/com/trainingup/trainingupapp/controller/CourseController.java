package com.trainingup.trainingupapp.controller;

import com.trainingup.trainingupapp.dto.CourseDTO;
import com.trainingup.trainingupapp.service.course_service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public class CourseController {
    @Autowired
    private CourseService courseService;

    @GetMapping("/course")
    public List<CourseDTO> introProject() {
        return courseService.findAll();
    }

    @CrossOrigin(origins = "*")
    @ResponseBody
    @PostMapping("/course/add")
    public CourseDTO addCoursePage(@RequestBody CourseDTO course) {
        return courseService.addCourse(course);
    }

    @ResponseBody
    @GetMapping("/course/remove")
    public void removeCourseByIdPage(@RequestParam("id") long id) {
        courseService.removeCourse(id);
    }

    @ResponseBody
    @GetMapping("/course/find")
    public CourseDTO findCourseByIdPage(@RequestParam("id") long id) {
        return courseService.findById(id);
    }
}
