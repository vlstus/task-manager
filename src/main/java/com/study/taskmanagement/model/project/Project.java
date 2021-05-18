package com.study.taskmanagement.model.project;

import com.study.taskmanagement.model.BaseEntity;
import com.study.taskmanagement.model.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "projects")
public class Project
        extends BaseEntity {

    @Column
    private String name;

    @Column
    @OneToMany
    @JoinColumn(name = "project_id")
    private List<Task> tasks;

    @OneToOne
    @JoinColumn(name = "manager_id", referencedColumnName = "id")
    private User manager;

}
