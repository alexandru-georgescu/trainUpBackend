package com.trainingup.trainingupapp.service.statistics.tm_statistics;

import com.trainingup.trainingupapp.dto.UserDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface TmStatisticsService {
    int rejected(UserDTO tm);
    int accepted(UserDTO tm);
    String predominantDomain(UserDTO tm);
}
