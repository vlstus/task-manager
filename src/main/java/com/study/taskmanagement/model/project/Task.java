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
@Table(name = "tasks")
public class Task
        extends BaseEntity {

    @Column
    private String name;

    @OneToOne
    @JoinColumn(name = "manager_id")
    private User manager;

    @OneToMany
    @JoinTable(name = "tasks_developers", joinColumns = {
            @JoinColumn(name = "developer_id", referencedColumnName = "id"),
            @JoinColumn(name = "task_id", referencedColumnName = "id")
    })
    private List<User> developers;

    @Column
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

}
