package com.study.taskmanagement.controller.rest.user;

import com.study.taskmanagement.dto.user.UserDTO;
import com.study.taskmanagement.model.user.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring") interface UserMapper {
    UserDTO convertUserToUserDTO(User user);
    User convertUserDTOToUser(UserDTO userDTO);
}
