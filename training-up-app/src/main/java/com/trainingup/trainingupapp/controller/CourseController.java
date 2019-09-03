package com.trainingup.trainingupapp.controller;

import com.trainingup.trainingupapp.dto.CourseDTO;
import com.trainingup.trainingupapp.dto.UserDTO;
import com.trainingup.trainingupapp.service.course_service.CourseService;
import com.trainingup.trainingupapp.service.user_service.UserService;
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

    @Autowired
    private UserService userService;


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
                .filter(courseDTO -> courseDTO.getEndDate().isBefore(now))
                .collect(Collectors.toList());
    }

    @PostMapping("/course/isFuture")
    public List<CourseDTO> findFuture(@RequestBody UserDTO user) {
        LocalDate now = LocalDate.now();

        UserDTO userDTO = userService.findById(user.getId());
        
        List<CourseDTO> all = courseService.findAll().stream()
                .filter(courseDTO -> courseDTO.getStartDate().isAfter(now))
                .collect(Collectors.toList());

        all.removeIf(c -> userDTO.getWishToEnroll()
                .stream()
                .filter( w -> w.getId() == c.getId()).findFirst()
                .orElse(null) != null);

        all.removeIf(c -> userDTO.getWaitToEnroll()
                .stream()
                .filter( w -> w.getId() == c.getId()).findFirst()
                .orElse(null) != null);

        all.removeIf(c -> userDTO.getRejectedList()
                .stream()
                .filter( w -> w.getId() == c.getId()).findFirst()
                .orElse(null) != null);

        all.removeIf(c -> userDTO.getCourses()
                .stream()
                .filter( w -> w.getId() == c.getId()).findFirst()
                .orElse(null) != null);

        return all;
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
