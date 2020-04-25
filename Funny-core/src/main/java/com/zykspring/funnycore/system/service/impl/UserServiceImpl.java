package com.zykspring.funnycore.system.service.impl;

import com.zykspring.funnycore.base.BaseService;
import com.zykspring.funnycore.system.dto.User;
import com.zykspring.funnycore.system.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl extends BaseService<User> implements UserService {

    @Override
    public int delete(String ids) {
        return 0;
    }

    @Override
    public List<User> select(User record, int pageNum, int pageSize) {
        return null;
    }
}
