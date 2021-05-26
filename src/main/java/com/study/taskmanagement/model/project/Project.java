package com.study.taskmanagement.model.project;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.study.taskmanagement.model.BaseEntity;
import com.study.taskmanagement.model.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
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
    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @NotFound(
            action = NotFoundAction.IGNORE
    )
    @JoinColumn(
            name = "manager_id",
            referencedColumnName = "id"
    )
    @NotNull
    private User manager;
    @OneToMany(mappedBy = "project")
    private List<Task> tasks;

}
