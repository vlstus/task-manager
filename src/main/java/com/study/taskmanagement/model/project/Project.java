package com.study.taskmanagement.model.project;

import com.study.taskmanagement.model.BaseEntity;
import com.study.taskmanagement.model.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "projects")
public class Project
        extends BaseEntity {

    @Column
    private String name;
    @ManyToOne
    @JoinColumn(name = "manager_id")
    private User manager;
    @OneToMany
    @JoinTable(
            name = "projects_tasks",
            joinColumns = {
                    @JoinColumn(
                            name = "project_id"
                    )
            },
            inverseJoinColumns = {
                    @JoinColumn(
                            name = "task_id"
                    )
            }
    )
    private List<Task> tasks;


}
