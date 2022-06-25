package com.study.taskmanagement.dto.project;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.study.taskmanagement.model.project.Task;
import com.study.taskmanagement.model.user.User;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

public record ProjectDTO(
        Integer id,
        @NotEmpty @Length(min = 5, max = 50) String name,
        @NotNull User manager,
        List<Task> tasks) {
    @JsonCreator public static ProjectDTO of(
            @JsonProperty("id") final Integer id,
            @JsonProperty("name") final String name,
            @JsonProperty("manager") final User manager,
            @JsonProperty("tasks") final List<Task> tasks) {
        return new ProjectDTO(id, name, manager, tasks);
    }
}