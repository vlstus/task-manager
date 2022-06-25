package com.study.taskmanagement.controller.rest.user;

import com.study.taskmanagement.controller.rest.MappingService;
import com.study.taskmanagement.dto.user.UserDTO;
import com.study.taskmanagement.model.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component class UserMappingService
        implements MappingService<User, UserDTO> {
    private final UserMapper userMapper;
    @Override public User convertToModel(final UserDTO transferObject) {
        return userMapper.convertUserDTOToUser(transferObject);
    }
    @Override public UserDTO convertToTransferObject(final User model) {
        return userMapper.convertUserToUserDTO(model);
    }
}
