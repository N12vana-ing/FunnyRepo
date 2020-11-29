package com.zykspring.funnycore.system.service.impl;

import com.zykspring.funnycore.base.BaseService;
import com.zykspring.funnycore.base.Mapper;
import com.zykspring.funnycore.system.dto.Authority;
import com.zykspring.funnycore.system.dto.User;
import com.zykspring.funnycore.system.mapper.MybatisUserMapper;
import com.zykspring.funnycore.system.service.ExportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Service("exportService")
public class ExportServiceImpl implements ExportService {

    private static final Logger log = LoggerFactory.getLogger(ExportServiceImpl.class);
    private final int maxSize = 5;
    private final int coreSize = 2;
    private final long keepAliveTime = 60L;
    @Autowired
    private Mapper<User> mapper;

    @Autowired
    private MybatisUserMapper mybatisUserMapper;

    private final ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
            coreSize,
            maxSize,
            keepAliveTime,
            TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(20)
    );

    @Override
    public boolean exportUploadFile(User user, Authority authority, String threadName) {
        threadPoolExecutor.execute(new ExportUploadFile(user,authority,threadName));
        return false;
    }

    @Override
    public boolean saveUploadList(List<User> users) {
        threadPoolExecutor.execute(new ExportUploadFile2(users,"ExportUploadFile2"));
        return false;
    }

    private void exportFileByThread(String threadName, User user, Authority authority){
        log.info("-------------------------------");
        log.info(threadName+" UserInfo ---> "+user.getUserId()+"  "+user.getUsername()+"  "+user.getPassword());
        log.info(threadName+" Authority ---> "+authority.getUserId()+"  "+authority.getAuthName()+"  "+authority.getAuthCode());
        log.info("-------------------------------");
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
    public void saveUserTestTransaction(List<User> users) throws Exception {
        for(int i = 0;i < users.size();i++){
//            mapper.insert(users.get(i));
            mybatisUserMapper.addOneUser(users.get(i));
        }
        throw new Exception("测试事务注解");
    }

    @Override
    public User getUserByID(Long id) {
        return mybatisUserMapper.selectUserById(id);
    }

    final class ExportUploadFile implements Runnable{


        private final User user;
        private final Authority authority;
        private final String threadName;

        public ExportUploadFile(User user, Authority authority, String threadName){
            this.user = user;
            this.authority = authority;
            this.threadName = threadName;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(2000);
            }catch (Exception e){
                log.error("threadName:"+threadName,e);
            }
            exportFileByThread(threadName, user, authority);
        }
    }

    final class ExportUploadFile2 implements Runnable{

        private final List<User> users;
        private final String threadName;

        public ExportUploadFile2(List<User> users, String threadName){
            this.users = users;
            this.threadName = threadName;
        }

        @Override
        public void run() {
            try {
                saveUserTestTransaction(users);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
