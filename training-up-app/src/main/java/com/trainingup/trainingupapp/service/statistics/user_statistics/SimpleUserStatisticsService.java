package com.trainingup.trainingupapp.service.statistics.user_statistics;

import com.trainingup.trainingupapp.controller.CourseController;
import com.trainingup.trainingupapp.dto.CourseDTO;
import com.trainingup.trainingupapp.dto.UserDTO;
import com.trainingup.trainingupapp.service.course_service.CourseService;
import com.trainingup.trainingupapp.service.statistics.tm_statistics.SortCourse;
import com.trainingup.trainingupapp.service.user_service.UserService;
import com.trainingup.trainingupapp.tables.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class SimpleUserStatisticsService implements UserStatisticsService {
    @Autowired
    UserService userService;

    @Autowired
    CourseService courseService;

    @Autowired
    CourseController courseController;

    @Override
    public List<Integer> courseStatistic(UserDTO user) {
        List<CourseDTO> allCourses = courseService.findBefore(user);

        AtomicInteger tech = new AtomicInteger(0);
        AtomicInteger soft = new AtomicInteger(0);
        AtomicInteger process = new AtomicInteger(0);

        allCourses.forEach(c -> {
            switch (c.getType()) {
                case TECH:
                    tech.set(tech.get() + 1);
                    break;
                case SOFT:
                    soft.set(soft.get() + 1);
                    break;
                case PROCESS:
                    process.set(process.get() + 1);
                    break;
            }
        });

        List<Integer> types = new ArrayList<>();
        types.add(tech.get());
        types.add(soft.get());
        types.add(process.get());
        return types;
    }

    @Override
    public int days(UserDTO user) {
        List<CourseDTO> courses = courseService.findCurrent(user);
        AtomicInteger days = new AtomicInteger(0);

        courses.forEach(c -> {
            days.set(days.get() + (int) Duration.between(c.getStartDate(), LocalDate.now()).toDays());
        });

        return days.get();
    }

    @Override
    public int daysLeft(UserDTO user) {
        List<CourseDTO> courses = courseService.findCurrent(user);
        AtomicInteger days = new AtomicInteger(0);

        courses.forEach(c -> {
            days.set(days.get() + (int) Duration.between(LocalDate.now(), c.getEndDate()).toDays());
        });

        return days.get();
    }
}
