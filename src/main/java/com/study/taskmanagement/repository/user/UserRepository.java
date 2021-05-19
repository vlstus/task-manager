package com.study.taskmanagement.repository.user;

import com.study.taskmanagement.model.user.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository
        extends CrudRepository<User, Integer> {

    @Modifying
    @Query(value =
            "INSERT INTO users (name,password,role_id) VALUES " +
                    "(:user_name,:password,(SELECT id FROM roles WHERE roles.type = :role_name))",
            nativeQuery = true)
    void createWithRole(@Param("user_name") String userName,
                        @Param("password") String password,
                        @Param("role_name") String roleName);

}
