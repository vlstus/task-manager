package com.study.taskmanagement.model.project;

import com.study.taskmanagement.model.BaseEntity;
import com.study.taskmanagement.model.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
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
