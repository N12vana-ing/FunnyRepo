package com.zykspring.funnycore.system.controller;

import com.zykspring.funnycore.base.BaseController;
import com.zykspring.funnycore.base.Result;
import com.zykspring.funnycore.system.dto.Authority;
import com.zykspring.funnycore.system.dto.User;
import com.zykspring.funnycore.system.service.ExportService;
import com.zykspring.funnycore.util.Results;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@Api(tags = "测试线程内部类")
@RequestMapping("/sys/export")
@RestController
public class ExportController extends BaseController {

    private static final Logger log = LoggerFactory.getLogger(ExportController.class);

    @Autowired
    ExportService exportService;

    /**
     * 当含有多个bean时，需要将这几个bean嵌套为一个bean进行传递
     * 因为@RequestBody只能够接受一个bean并转换
     *
     * 请求中数据需要这样发送
     * {
     *     "userId":1,
     *     "username":"zhouTest",
     *     "password":"authCode",
     *     "authority":{
     *         "userId":1,
     *         "authCode":"0000",
     *         "authName":"超级管理员"
     *     }
     * }
     * @param list
     * @return
     */

    @PostMapping(value = "/save", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Result save(@Valid @RequestBody List<User> list){
        int i = 0;
        for (User user:list){
            Authority authority = user.getAuthority();
            log.info("controller=UserInfo ---> "+user.getUserId()+"  "+user.getUsername()+"  "+user.getPassword());
            log.info("controller=Authority ---> "+authority.getUserId()+"  "+authority.getAuthName()+"  "+authority.getAuthCode());
            exportService.exportUploadFile(user, user.getAuthority(), "Thread-"+i);
            i++;
        }
        return Results.successWithData(list);
    }

    @PostMapping(value = "/saveList", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Result saveList(@Valid @RequestBody List<User> list){
        int i = 0;
        for (User user:list){
            Authority authority = user.getAuthority();
            log.info("controller=UserInfo ---> "+user.getUserId()+"  "+user.getUsername()+"  "+user.getPassword());
            log.info("controller=Authority ---> "+authority.getUserId()+"  "+authority.getAuthName()+"  "+authority.getAuthCode());
//            if(i==0){
                //创造异常
                user.setCreateBy((long)-1);
                user.setUpdateBy((long)-1);
                user.setCreateDate(new Date());
                user.setUpdateDate(new Date());
                user.setEnabled(0);
                user.setSex(0);
//            }
            i++;
        }
        try {
//            User user = exportService.getUserByID((long)1);
//            log.info(user.toJSONString());
            exportService.saveUserTestTransaction(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Results.successWithData(list);
    }

}
