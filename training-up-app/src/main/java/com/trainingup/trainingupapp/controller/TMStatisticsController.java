package com.trainingup.trainingupapp.controller;

import com.trainingup.trainingupapp.dto.UserDTO;
import com.trainingup.trainingupapp.service.course_service.CourseService;
import com.trainingup.trainingupapp.service.statistics.tm_statistics.TmStatisticsService;
import com.trainingup.trainingupapp.service.user_service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
public class TMStatisticsController {

    @Autowired
    TmStatisticsService statisticsService;

    @PostMapping("/tm_statistics/rejected")
    public int rejected(@RequestBody UserDTO tm) {
        return statisticsService.rejected(tm);
    }

    @PostMapping("/tm_statistics/accepted")
    public int accepted(@RequestBody UserDTO tm) {
        return statisticsService.accepted(tm);
    }

    @PostMapping("/tm_statistics/predominant_domain")
    public List<String> predominantDomain(@RequestBody UserDTO tm) {
        return statisticsService.predominantDomain(tm);
    }


}
