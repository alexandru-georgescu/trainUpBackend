package com.trainingup.trainingupapp.service.statistics.user_statistics;

import com.trainingup.trainingupapp.service.course_service.CourseService;
import com.trainingup.trainingupapp.service.user_service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SimpleUserStatisticsService implements UserStatisticsService{
    @Autowired
    UserService userService;

    @Autowired
    CourseService courseService;
}
