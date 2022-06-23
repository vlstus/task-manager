package com.study.taskmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class TaskManagementApplication {

    public static void main(String[] args) {
        final var context = SpringApplication.run(TaskManagementApplication.class, args);
    }

}
