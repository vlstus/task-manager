package com.study.taskmanagement.model.project;

import com.study.taskmanagement.model.BaseEntity;
import com.study.taskmanagement.model.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Data
@NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name = "projects")
public class Project
        extends BaseEntity {

    private String name;
    @ManyToOne
    @JoinColumn(name = "manager_id")
    private User manager;
    @OneToMany(mappedBy = "project")
    private List<Task> tasks;

}
