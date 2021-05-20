package com.study.taskmanagement.service.admin;

import com.study.taskmanagement.model.user.User;
import com.study.taskmanagement.repository.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import static com.study.taskmanagement.util.EntityUtils.*;

// TODO: 19-May-21 Change exceptions to BusinessException from developer service branch

@Service
@AllArgsConstructor
public class SimpleAdminService
        implements AdminService {

    private final UserRepository userRepository;

    @Override
    public User create(User user) {
        assureNew(user);
        return userRepository.save(user);
    }

    @Override
    public User update(User user) {
        assurePersisted(user);
        return userRepository.save(user);
    }

    @Override
    public User get(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(RuntimeException::new);
    }

    @Override
    public void delete(User user) {
        assurePersisted(user);
        userRepository.delete(user);
    }


}
