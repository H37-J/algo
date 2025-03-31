package hjk.algo.mapper;

import hjk.algo.model.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    User selectUser();

    User login(String loginId);
}
