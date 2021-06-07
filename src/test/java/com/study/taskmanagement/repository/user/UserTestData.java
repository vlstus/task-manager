package com.study.taskmanagement.repository.user;

import com.study.taskmanagement.CopyUtils;
import com.study.taskmanagement.model.user.Role;
import com.study.taskmanagement.model.user.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class UserTestData {

    public static final class RoleTestData {

        public static final int DEVELOPER_ROLE_ID = 100_000;
        public static final int MANAGER_ROLE_ID = DEVELOPER_ROLE_ID + 1;
        public static final int ADMIN_ROLE_ID = MANAGER_ROLE_ID + 1;

        public static final Role DEVELOPER_ROLE = Role.valueOf("ROLE_DEVELOPER");
        public static final Role MANAGER_ROLE = Role.valueOf("ROLE_MANAGER");
        public static final Role ADMIN_ROLE = Role.valueOf("ROLE_ADMIN");

    }

    public static final int TEST_DEVELOPER_ID = 100_000;
    public static final int TEST_MANAGER_ID = 100_001;
    public static final int TEST_ADMIN_ID = 100_002;
    public static final User TEST_DEVELOPER = new User("John Doe", "Password", RoleTestData.DEVELOPER_ROLE);
    public static final User TEST_MANAGER = new User("Jane Doe", "Password", RoleTestData.MANAGER_ROLE);
    public static final User TEST_ADMIN = new User("Joe Doe", "Password", RoleTestData.ADMIN_ROLE);

    static {
        TEST_DEVELOPER.setId(TEST_DEVELOPER_ID);
        TEST_MANAGER.setId(TEST_MANAGER_ID);
        TEST_ADMIN.setId(TEST_ADMIN_ID);
    }

    public static final List<User> TEST_USERS = new ArrayList<>(Arrays.asList(TEST_DEVELOPER, TEST_MANAGER, TEST_ADMIN));

    public static User copyOf(User user) {
        return CopyUtils.copyOf(user, User.class);
    }

}
