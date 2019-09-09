package com.trainingup.trainingupapp.service.statistics.user_statistics;

import com.trainingup.trainingupapp.dto.UserDTO;

import java.util.List;

public interface UserStatisticsService {
    List<String> findBestCourseFromPast(UserDTO user);
    List<String> findBestCourse();
    void addDomain(String domain);
}
