package com.study.taskmanagement.dto.user;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.study.taskmanagement.model.user.Role;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public record UserDTO(
        Integer id,
        @NotEmpty @Length(min = 4, max = 25) String name,
        @Length(min = 5, max = 100) String password,
        @NotNull Role role) {
    @JsonCreator public static UserDTO of(
            @JsonProperty("id") final Integer id,
            @JsonProperty("name") final String name,
            @JsonProperty("password") final String password,
            @JsonProperty("role") final Role role) {
        return new UserDTO(id, name, password, role);
    }
}
