package com.study.taskmanagement.service;

import com.study.taskmanagement.BaseTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public abstract class BaseServiceTest
        extends BaseTest {

}
