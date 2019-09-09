package com.trainingup.trainingupapp.service.statistics.tm_statistics;

import com.trainingup.trainingupapp.dto.UserDTO;

import java.util.List;

public interface TmStatisticsService {
    int rejected(UserDTO tm);
    int accepted(UserDTO tm);
    List<String> predominantDomain(UserDTO tm);
    void addDomain(String domain);
    String teamPercentage(UserDTO tm);
}
