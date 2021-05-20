package com.study.taskmanagement.repository.project;

import com.study.taskmanagement.model.project.Project;
import com.study.taskmanagement.model.project.Status;
import com.study.taskmanagement.model.project.Task;
import com.study.taskmanagement.model.user.User;
import com.study.taskmanagement.repository.user.UserTestData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.study.taskmanagement.repository.project.ProjectTestData.TaskTestData.TEST_TASK;
import static com.study.taskmanagement.repository.user.UserTestData.TEST_DEVELOPER;
import static com.study.taskmanagement.repository.user.UserTestData.TEST_MANAGER;

public final class ProjectTestData {

    public static final class StatusTestData {

        public static final int TEST_STATUS_ID = 100_000;

        public static final Status TEST_STATUS = Status.ofType("TO_DO");

        static {
            TEST_STATUS.setId(TEST_STATUS_ID);
        }

    }

    public static final class TaskTestData {

        public static final int TEST_TASK_ID = 100_000;

        public static final Task TEST_TASK = new Task("DESIGN DOMAIN MODEL",
                UserTestData.copyOf(TEST_MANAGER),
                StatusTestData.TEST_STATUS,
                new ArrayList<>(Collections.singletonList(UserTestData.copyOf(TEST_DEVELOPER))));

        static {
            TEST_TASK.setId(TEST_TASK_ID);
        }

        public static Task copyOf(Task task) {
            Task copy = new Task();
            copy.setId(task.getId());
            copy.setName(task.getName());
            copy.setStatus(task.getStatus());
            copy.setManager(UserTestData.copyOf(task.getManager()));
            copy.getManager().setId(UserTestData.TEST_MANAGER_ID);
            List<User> developersCopy = new ArrayList<>();
            task.getDevelopers().forEach(developer -> developersCopy.add(UserTestData.copyOf(developer)));
            copy.setDevelopers(developersCopy);
            return copy;
        }

    }

    public static final int TEST_PROJECT_ID = 100_000;

    public static final Project TEST_PROJECT = new Project("Task Management",
            UserTestData.copyOf(TEST_MANAGER),
            new ArrayList<>(Collections.singletonList(TaskTestData.copyOf(TEST_TASK))));

    static {
        TEST_PROJECT.setId(TEST_PROJECT_ID);
    }

    public static Project copyOf(Project project) {
        Project copy = new Project();
        copy.setId(project.getId());
        copy.setName(project.getName());
        copy.setManager(UserTestData.copyOf(project.getManager()));
        List<Task> tasksCopy = new ArrayList<>();
        project.getTasks().forEach(task -> tasksCopy.add(TaskTestData.copyOf(task)));
        copy.setTasks(tasksCopy);
        return copy;
    }

}
