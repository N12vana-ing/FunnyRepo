package com.zykspring.funnycore.system.service;

import com.zykspring.funnycore.system.dto.Authority;
import com.zykspring.funnycore.system.dto.User;

import java.util.List;

public interface ExportService {
    boolean exportUploadFile(User user, Authority authority, String threadName);

    boolean saveUploadList(List<User> users);

    void saveUserTestTransaction(List<User> users) throws Exception;

    User getUserByID(Long id);
}
