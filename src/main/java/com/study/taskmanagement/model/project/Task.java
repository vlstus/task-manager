package com.study.taskmanagement.model.project;

import com.study.taskmanagement.model.BaseEntity;
import com.study.taskmanagement.model.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Data
@NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name = "tasks")
public class Task
        extends BaseEntity {

    private String name;
    @ManyToOne
    @JoinColumn(name = "manager_id")
    private User manager;
    @Enumerated(EnumType.STRING)
    private Status status;
    @ManyToOne
    @JoinColumn(name = "developer_id")
    private User developer;
    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

}
