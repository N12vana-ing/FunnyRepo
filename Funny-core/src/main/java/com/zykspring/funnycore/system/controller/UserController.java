package com.zykspring.funnycore.system.controller;

import com.zykspring.funnycore.Constants;
import com.zykspring.funnycore.base.BaseController;
import com.zykspring.funnycore.constants.BaseEnums;
import com.zykspring.funnycore.base.Result;
import com.zykspring.funnycore.system.dto.User;
import com.zykspring.funnycore.system.service.UserService;
import com.zykspring.funnycore.util.Dates;
import com.zykspring.funnycore.util.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户Controller
 *
 * @version 1.0
 * @author bojiangzhou 2017-12-31
 */
@RequestMapping("/sys/user")
@RestController
public class UserController extends BaseController {

    @Autowired
    private UserService userService;


    @PostMapping("/queryAll")
    public Result queryAll(){
        List<User> list = userService.selectAll();
        return Results.successWithData(list, BaseEnums.SUCCESS.code(), BaseEnums.SUCCESS.desc());
    }

    @RequestMapping("/queryOne/{userId}")
    public Result queryOne(@PathVariable Long userId){
        User user = userService.get(userId);
        return Results.successWithData(user);
    }

    @PostMapping("/save")
    public Result save(@Valid @RequestBody User user){
        user = userService.insertSelective(user);
        return Results.successWithData(user);
    }

    @PostMapping("/update")
    public Result update(@Valid @RequestBody List<User> user){
        user = userService.persistSelective(user);
        return Results.successWithData(user);
    }

    @RequestMapping("/delete")
    public Result delete(User user){
        userService.delete(user);
        return Results.success();
    }

    @RequestMapping("/delete/{userId}")
    public Result delete(@PathVariable Long userId){
        userService.delete(userId);
        return Results.success();
    }

}
