package com.trainingup.trainingupapp.service.course_service;

import com.trainingup.trainingupapp.convertor.CourseConvertor;
import com.trainingup.trainingupapp.dto.CourseDTO;
import com.trainingup.trainingupapp.dto.UserDTO;
import com.trainingup.trainingupapp.repository.CourseRepository;
import com.trainingup.trainingupapp.service.user_service.UserService;
import com.trainingup.trainingupapp.tables.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SimpleCourseService implements CourseService {

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    UserService userService;


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
    public Course findByIdDB(long id) {
        return courseRepository
                .findAll()
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
        CourseDTO dummy = findById(id);
        if (dummy != null) {
            this.backendCourses.remove(dummy);
        }
    }

    @Override
    public List<CourseDTO> findCurrent(UserDTO userDTO) {
        LocalDate now = LocalDate.now();

        return userDTO.getCourses().stream()
                .filter(courseDTO -> courseDTO.getEndDate().isAfter(now))
                .collect(Collectors.toList());
    }

    @Override
    public List<CourseDTO> findBefore(UserDTO userDTO) {
        LocalDate now = LocalDate.now();

        return userDTO.getCourses().stream()
                .filter(courseDTO -> courseDTO.getEndDate().isBefore(now))
                .collect(Collectors.toList());
    }

    @Override
    public List<CourseDTO> findFuture(UserDTO userDTO) {
        LocalDate now = LocalDate.now();

        List<CourseDTO> all = backendCourses.stream()
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

    @Override
    public List<CourseDTO> findByPm(UserDTO pm) {
        return backendCourses
                .stream()
                .filter(c -> c.getProjectManager().toLowerCase().equals(pm.getEmail().toLowerCase()))
                .collect(Collectors.toList());
    }

    @Override
    public void saveAndFlash(Course course) {
        courseRepository.save(course);
    }

    @Override
    public void saveAndFlashBack(CourseDTO courseDTO) {
        backendCourses = backendCourses.stream().map(c -> {
            if (c.getId() == courseDTO.getId()) {
                c = courseDTO;
            }
            return c;
        }).collect(Collectors.toList());
    }

    @Override
    public List<UserDTO> findAcceptedByPm(CourseDTO course) {
        CourseDTO backendDTO = findById(course.getId());

        return userService
                .findAll()
                .stream()
                .filter(u -> u.getCourses().contains(backendDTO))
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDTO> findRejectedByPm(CourseDTO course) {
        CourseDTO backendDTO = findById(course.getId());

        return userService
                .findAll()
                .stream()
                .filter(u -> u.getRejectedList().contains(backendDTO))
                .collect(Collectors.toList());
    }
}
