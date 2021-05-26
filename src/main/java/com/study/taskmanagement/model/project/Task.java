package com.study.taskmanagement.model.project;

import com.study.taskmanagement.model.BaseEntity;
import com.study.taskmanagement.model.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tasks")
public class Task
        extends BaseEntity {

    @NotEmpty
    @Length(min = 5, max = 50)
    private String name;
    @ManyToOne
    @JoinColumn(name = "manager_id")
    @NotNull
    private User manager;
    @Enumerated(EnumType.STRING)
    @NotNull
    private Status status;
    @ManyToOne
    @JoinColumn(name = "developer_id")
    @NotNull
    private User developer;
    @ManyToOne
    @JoinColumn(name = "project_id")
    @NotNull
    private Project project;

}
