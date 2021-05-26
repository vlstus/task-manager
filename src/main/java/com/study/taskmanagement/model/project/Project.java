package com.study.taskmanagement.model.project;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.study.taskmanagement.model.BaseEntity;
import com.study.taskmanagement.model.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@ToString(exclude = {
        "tasks"
})
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "projects")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Project
        extends BaseEntity {

    @NotEmpty
    @Length(min = 5, max = 50)
    private String name;
    @ManyToOne
    @JoinColumn(name = "manager_id")
    @NotNull
    private User manager;
    @OneToMany(mappedBy = "project")
    private List<Task> tasks;

}
