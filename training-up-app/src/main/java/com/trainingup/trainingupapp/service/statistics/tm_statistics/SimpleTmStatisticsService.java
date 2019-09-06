package com.trainingup.trainingupapp.service.statistics.tm_statistics;

import com.trainingup.trainingupapp.dto.UserDTO;
import com.trainingup.trainingupapp.service.course_service.CourseService;
import com.trainingup.trainingupapp.service.user_service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SimpleTmStatisticsService implements TmStatisticsService {
    @Autowired
    UserService userService;

    @Autowired
    CourseService courseService;

    @Override
    public int rejected(UserDTO tm) {
        return userService.findByName(tm.getEmail()).getRejected();
    }

    @Override
    public int accepted(UserDTO tm) {
        return userService.findByName(tm.getEmail()).getAccepted();
    }

    @Override
    public String predominantDomain(UserDTO tm) {
        List<UserDTO> allUsers = userService.findAllWithLeader(tm.getEmail());

        return null;
    }
}
