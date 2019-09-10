package com.trainingup.trainingupapp.service.statistics.pm_statistics;

import com.trainingup.trainingupapp.dto.CourseDTO;
import com.trainingup.trainingupapp.dto.UserDTO;
import com.trainingup.trainingupapp.service.course_service.CourseService;
import com.trainingup.trainingupapp.service.user_service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class SimplePmStatisticsService implements PmStatisticsService {
    @Autowired
    UserService userService;

    @Autowired
    CourseService courseService;

    @Override
    public String courseAverage(UserDTO pm) {
        List<CourseDTO> courses = courseService
                .findAll()
                .stream()
                .filter(c -> pm.getEmail().equals(c.getProjectManager()))
                .collect(Collectors.toList());

        AtomicInteger cap = new AtomicInteger();
        AtomicInteger actualCap = new AtomicInteger();

        courses.forEach(c -> {
            cap.addAndGet(c.getCapacity());
            actualCap.addAndGet(c.getActualCapacity());
        });

        int finalValue = 100 - actualCap.get()/cap.get();

        return String.valueOf(finalValue);
    }
}
