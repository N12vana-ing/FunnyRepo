package com.zykspring.funnycore.system.service.impl;

import com.zykspring.funnycore.base.BaseService;
import com.zykspring.funnycore.plugins.MyRedisAnno;
import com.zykspring.funnycore.system.dto.User;
import com.zykspring.funnycore.system.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("UserServiceImpl")
public class UserServiceImpl extends BaseService<User> implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public int delete(String ids) {
        return 0;
    }

    @Override
    public List<User> select(User record, int pageNum, int pageSize) {
        return null;
    }

    @Override
//    @Cacheable(value="User",key="#id",condition="#id != null",unless="#result == null")
    @MyRedisAnno
    public User get(Long id) {
        logger.info("in redis get one user "+id);
        return super.get(id);
    }
}
