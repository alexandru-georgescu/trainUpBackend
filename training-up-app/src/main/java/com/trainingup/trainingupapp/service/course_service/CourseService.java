package com.trainingup.trainingupapp.service.course_service;

import com.trainingup.trainingupapp.dto.CourseDTO;
import com.trainingup.trainingupapp.dto.UserDTO;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface CourseService {
    List<CourseDTO> findAll();
    CourseDTO findById(long id);
    CourseDTO addCourse(CourseDTO course);
    void removeCourse(long id);
    List<CourseDTO> findCurrent(UserDTO userDTO);
    List<CourseDTO> findBefore(@RequestBody UserDTO userDTO);
    List<CourseDTO> findFuture(@RequestBody UserDTO user);
    List<CourseDTO> findByPm(@RequestBody UserDTO pm);
}
