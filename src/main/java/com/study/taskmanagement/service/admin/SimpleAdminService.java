package com.study.taskmanagement.service.admin;

import com.study.taskmanagement.model.user.User;
import com.study.taskmanagement.repository.user.UserRepository;
import com.study.taskmanagement.service.exception.BusinessLogicException;
import com.study.taskmanagement.service.logging.annotation.LogMethodNameAndParameters;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import static com.study.taskmanagement.util.EntityUtils.*;


@Service
@AllArgsConstructor
public class SimpleAdminService
        implements AdminService {

    private final UserRepository userRepository;

    @Override
    @LogMethodNameAndParameters
    public User create(User user) {
        assureNew(user);
        return userRepository.save(user);
    }

    @Override
    @LogMethodNameAndParameters
    public User update(User user) {
        assurePersisted(user);
        return userRepository.save(user);
    }

    @Override
    @LogMethodNameAndParameters
    public User get(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(BusinessLogicException::new);
    }

    @Override
    @LogMethodNameAndParameters
    public void delete(User user) {
        assurePersisted(user);
        translateException(() -> userRepository.delete(user));
    }


}
