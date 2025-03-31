package hjk.algo.service;

import hjk.algo.mapper.UserMapper;
import hjk.algo.model.User;
import hjk.algo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User selectUser() {
        return userRepository.selectUser();
    }

    public User login(String loginId) {
        return userRepository.login(loginId);
    }
}
