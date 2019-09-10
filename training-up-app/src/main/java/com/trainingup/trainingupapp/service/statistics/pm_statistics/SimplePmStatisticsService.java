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
        cap.getAndSet(0);

        AtomicInteger actualCap = new AtomicInteger();
        actualCap.getAndSet(0);
        courses.forEach(c -> {
            cap.addAndGet(c.getCapacity());
            actualCap.addAndGet(c.getActualCapacity());
        });

        float finalValue = (float) (100 - 1F* actualCap.get()/cap.get() * 100);

        return String.valueOf(finalValue);
    }
}
