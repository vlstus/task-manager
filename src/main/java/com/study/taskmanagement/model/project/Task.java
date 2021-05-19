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
    @ManyToOne
    @JoinColumn(name = "manager_id")
    private User manager;
    @OneToOne
    @JoinColumn(name = "status_id")
    private Status status;
    @OneToMany
    @JoinTable(
            name = "tasks_developers",
            joinColumns = {
                    @JoinColumn(
                            name = "task_id"
                    )
            },
            inverseJoinColumns = {
                    @JoinColumn(
                            name = "developer_id"
                    )
            }
    )
    private List<User> developers;


}
