package com.trainingup.trainingupapp.service.outlook_service;

import com.trainingup.trainingupapp.dto.CourseDTO;
import com.trainingup.trainingupapp.dto.UserDTO;

public interface InvitationService {

    public void send(UserDTO user, CourseDTO courseDTO);
}
