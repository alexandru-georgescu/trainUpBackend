package com.trainingup.trainingupapp.convertor;

import com.trainingup.trainingupapp.dto.UserDTO;
import com.trainingup.trainingupapp.tables.User;

public class UserConvertor {
    public static User convertToUser(UserDTO user) {
        User convertedUser = new User();
        convertedUser.setType(user.getType());
        convertedUser.setPassword(user.getPassword());
        convertedUser.setLastName(user.getLastName());
        convertedUser.setFirstName(user.getFirstName());
        convertedUser.setEmail(user.getEmail());
        convertedUser.setId(user.getId());
        return convertedUser;
    }

    public static UserDTO convertToUserDTO(User user) {
        UserDTO convertedUser = new UserDTO();
        convertedUser.setType(user.getType());
        convertedUser.setPassword(user.getPassword());
        convertedUser.setLastName(user.getLastName());
        convertedUser.setFirstName(user.getFirstName());
        convertedUser.setEmail(user.getEmail());
        convertedUser.setId(user.getId());
        return convertedUser;
    }
}
