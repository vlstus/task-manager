package com.study.taskmanagement.repository.project;

import com.study.taskmanagement.CopyUtils;
import com.study.taskmanagement.model.project.Project;
import com.study.taskmanagement.model.project.Status;
import com.study.taskmanagement.model.project.Task;

import java.util.ArrayList;
import java.util.Collections;

import static com.study.taskmanagement.repository.project.ProjectTestData.TaskTestData.TEST_TASK;
import static com.study.taskmanagement.repository.user.UserTestData.TEST_DEVELOPER;
import static com.study.taskmanagement.repository.user.UserTestData.TEST_MANAGER;

public final class ProjectTestData {

    public static final class TaskTestData {

        public static final int TEST_TASK_ID = 100_000;

        public static final Task TEST_TASK = new Task("DESIGN DOMAIN MODEL",
                TEST_MANAGER,
                Status.TO_DO,
                TEST_DEVELOPER,
                TEST_PROJECT);

        static {
            TEST_TASK.setId(TEST_TASK_ID);
        }

        public static Task copyOf(Task task) {
            return CopyUtils.copyOf(task, Task.class);
        }

    }

    public static final int TEST_PROJECT_ID = 100_000;

    public static final Project TEST_PROJECT = new Project("Task Management",
            TEST_MANAGER,
            new ArrayList<>(Collections.singletonList(TEST_TASK)));

    static {
        TEST_PROJECT.setId(TEST_PROJECT_ID);
    }

    public static Project copyOf(Project project) {
        return CopyUtils.copyOf(project, Project.class);
    }

}
