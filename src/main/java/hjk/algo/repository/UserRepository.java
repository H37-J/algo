package hjk.algo.repository;

import hjk.algo.mapper.UserMapper;
import hjk.algo.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserRepository {
    private final UserMapper userMapper;

    public User selectUser() {
        return userMapper.selectUser();
    }

    public User login(String loginId) {
        return userMapper.login(loginId);
    }
}
