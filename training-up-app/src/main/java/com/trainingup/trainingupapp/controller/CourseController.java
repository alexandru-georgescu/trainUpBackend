package com.trainingup.trainingupapp.controller;

import com.trainingup.trainingupapp.dto.CourseDTO;
import com.trainingup.trainingupapp.dto.UserDTO;
import com.trainingup.trainingupapp.service.course_service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*")
public class CourseController {
    @Autowired
    private CourseService courseService;

    @GetMapping("/course")
    public List<CourseDTO> introProject() {
        return courseService.findAll();
    }


    @PostMapping("/course/isCurrent")
    public List<CourseDTO> findCurrent(@RequestBody UserDTO userDTO) {
        LocalDate now = LocalDate.now();

        return userDTO.getCourses().stream()
                .filter(courseDTO -> courseDTO.getEndDate().isAfter(now)
                        && courseDTO.getStartDate().isBefore(now))
                .collect(Collectors.toList());
    }


    @PostMapping("/course/isBefore")
    public List<CourseDTO> findBefore(@RequestBody UserDTO userDTO) {
        LocalDate now = LocalDate.now();

        return userDTO.getCourses().stream()
                .filter(courseDTO -> courseDTO.getEndDate().isAfter(now))
                .collect(Collectors.toList());
    }

    @PostMapping("/course/add")
    public CourseDTO addCoursePage(@RequestBody CourseDTO course) {
        return courseService.addCourse(course);
    }

    @GetMapping("/course/remove")
    public void removeCourseByIdPage(@RequestParam("id") long id) {
        courseService.removeCourse(id);
    }

    @GetMapping("/course/find")
    public CourseDTO findCourseByIdPage(@RequestParam("id") long id) {
        return courseService.findById(id);
    }
}
