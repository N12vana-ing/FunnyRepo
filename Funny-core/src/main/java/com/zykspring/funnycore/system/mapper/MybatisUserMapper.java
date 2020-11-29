package com.zykspring.funnycore.system.mapper;

import com.zykspring.funnycore.system.dto.User;
import org.springframework.stereotype.Repository;

@Repository
public interface MybatisUserMapper {
    User selectUserById(Long id);
    Long addOneUser(User user);
}
