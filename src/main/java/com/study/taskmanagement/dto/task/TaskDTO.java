package com.study.taskmanagement.dto.task;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.study.taskmanagement.model.project.Project;
import com.study.taskmanagement.model.project.Status;
import com.study.taskmanagement.model.user.User;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public record TaskDTO(
        Integer id,
        @NotEmpty @Length(min = 5, max = 50) String name,
        @NotNull User manager,
        @NotNull Status status,
        @NotNull User developer,
        @NotNull Project project) {
    @JsonCreator public static TaskDTO of(
            @JsonProperty("id") final Integer id,
            @JsonProperty("name") final String name,
            @JsonProperty("manager") final User manager,
            @JsonProperty("status") final Status status,
            @JsonProperty("developer") final User developer,
            @JsonProperty("project") final Project project) {
        return new TaskDTO(id, name, manager, status, developer, project);
    }
}
