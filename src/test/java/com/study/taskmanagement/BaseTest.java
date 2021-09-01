package com.study.taskmanagement;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import org.h2.tools.Server;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;

import java.sql.SQLException;

@ActiveProfiles("test")
public class BaseTest {

    @TestConfiguration
    static class BaseRepositoryTestConfiguration {

        @Bean
        public Module hibernateLazyProxyModule() {
            return new Hibernate5Module();
        }

        @Bean(
                initMethod = "start",
                destroyMethod = "stop"
        )
        public Server databaseServer()
                throws SQLException {
            return Server.createTcpServer("-tcp", "-tcpAllowOthers", "-tcpPort", "9090");
        }

    }

}
