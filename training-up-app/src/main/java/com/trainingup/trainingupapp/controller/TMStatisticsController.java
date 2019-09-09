package com.trainingup.trainingupapp.controller;

import com.trainingup.trainingupapp.dto.UserDTO;
import com.trainingup.trainingupapp.service.course_service.CourseService;
import com.trainingup.trainingupapp.service.statistics.tm_statistics.TmStatisticsService;
import com.trainingup.trainingupapp.service.statistics.user_statistics.UserStatisticsService;
import com.trainingup.trainingupapp.service.user_service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
public class TMStatisticsController {

    @Autowired
    TmStatisticsService tmStatisticsService;
    
    @Autowired
    UserStatisticsService userStatisticsService;

    @PostMapping("/tm_statistics/rejected")
    public int rejected(@RequestBody UserDTO tm) {
        return tmStatisticsService.rejected(tm);
    }

    @PostMapping("/tm_statistics/accepted")
    public int accepted(@RequestBody UserDTO tm) {
        return tmStatisticsService.accepted(tm);
    }

    @PostMapping("/tm_statistics/predominant_domain")
    public List<String> predominantDomain(@RequestBody UserDTO tm) {
        return tmStatisticsService.predominantDomain(tm);
    }

    @GetMapping("/user_statistics/findBestCourse")
    public List<String> findBestCourse() {
        return userStatisticsService.findBestCourse();
    }

    @PostMapping("/user_statistics/findBestCourseFromPast")
    public List<String> findBestCourseFromPast(@RequestBody UserDTO user) {
        return userStatisticsService.findBestCourseFromPast(user);
    }


}
