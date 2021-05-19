package com.study.taskmanagement.service.developer;

import com.study.taskmanagement.model.project.Status;
import com.study.taskmanagement.model.project.Task;
import com.study.taskmanagement.repository.project.ProjectTestData;
import com.study.taskmanagement.repository.project.StatusRepository;
import com.study.taskmanagement.repository.project.TaskRepository;
import com.study.taskmanagement.service.BaseServiceTest;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;
import org.springframework.context.annotation.Bean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@MockBeans({
        @MockBean(TaskRepository.class),
        @MockBean(StatusRepository.class)
})
/*
https://stackoverflow.com/questions/47434183/mockito-tests-pass-individually-but-fail-as-part-of-a-suite
https://stackoverflow.com/questions/18164123/how-to-clean-up-mocks-in-spring-tests-when-using-mockito
* */
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class DeveloperServiceTest
        extends BaseServiceTest {

    @TestConfiguration
    static class DeveloperServiceTestConfiguration {

        @Autowired
        TaskRepository taskRepository;
        @Autowired
        StatusRepository statusRepository;

        @Bean
        public DeveloperService developerService() {
            initMock();
            return new SimpleDeveloperService(taskRepository);
        }

        private void initMock() {
            final Status testStatus = Status.ofType("IN_PROGRESS");
            final int testStatusId = 100_000;
            testStatus.setId(testStatusId);
            Mockito.when(statusRepository.findByStatusType(testStatus.getStatusType())).
                    thenReturn(Optional.of(testStatus));
            final Task testTask = ProjectTestData.TaskTestData.copyOf(ProjectTestData.TaskTestData.TEST_TASK);
            testTask.setStatus(testStatus);
            Mockito.when(taskRepository.findById(ProjectTestData.TaskTestData.TEST_TASK_ID))
                    .thenReturn(Optional.of(testTask));
            Mockito.when(taskRepository.save(Mockito.any(Task.class)))
                    .thenAnswer((Answer<Task>) invocation -> invocation.getArgument(0));
        }

    }

    @Autowired
    DeveloperService developerService;

    @Test
    void getTaskStatusTest() {
        assertThat(developerService.getStatusOf(ProjectTestData.TaskTestData.TEST_TASK_ID))
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(ProjectTestData.TaskTestData.TEST_TASK.getStatus());
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    void setTaskStatusTest() {
        assertThat(developerService.getStatusOf(ProjectTestData.TaskTestData.TEST_TASK_ID))
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(Status.ofType("IN_PROGRESS"));
        Status newStatus = Status.ofType("DONE");
        developerService.setStatusOf(ProjectTestData.TaskTestData.TEST_TASK_ID, newStatus);
        assertThat(developerService.getStatusOf(ProjectTestData.TaskTestData.TEST_TASK_ID))
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(newStatus);
    }

}
