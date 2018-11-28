package com.andon.securitydemo.controller;

import com.andon.securitydemo.domain.User;
import com.andon.securitydemo.service.UserService;
import com.andon.securitydemo.util.SecurityResponse;
import com.andon.securitydemo.util.UserRoles;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 用户管理
 */
@RestController
@RequestMapping(value = "/security")
public class UserController {

    @Resource
    private UserService userService;

    /**
     * 获取所有用户信息
     */
    @PostMapping(value = "/user/findAll")
    public SecurityResponse findAll() {
        List<User> users = userService.findAll();
        return new SecurityResponse("1", "Find all user success!!", users);
    }

    /**
     * 新增用户
     */
    @PostMapping(value = "/user/add")
    public SecurityResponse add(User user) {
        User userExit = userService.findByUsername(user.getUsername());
        if (ObjectUtils.isEmpty(userExit)) {
            User save = userService.save(user);
            return new SecurityResponse("1", "Add user success!!", save);
        }
        return new SecurityResponse("-1", "User already exists!!", userExit);
    }

    /**
     * 修改用户信息
     */
    @PostMapping(value = "/user/update")
    public SecurityResponse update(User user) {
        User save = userService.save(user);
        return new SecurityResponse("1", "Update user success!!", save);
    }

    /**
     * 删除用户
     */
    @PostMapping(value = "/user/delete")
    public SecurityResponse delete(Long id) {
        userService.delete(id);
        return new SecurityResponse("1", "Delete user success!!", id);
    }

    /**
     * 为用户添加角色
     */
    @PostMapping(value = "/user/addRoles")
    public SecurityResponse addRoles(@RequestBody UserRoles userRoles) {
        System.out.println(userRoles);
        userService.addRoles(userRoles.getUserId(), userRoles.getRoleIds());
        return new SecurityResponse("1", "User add role success!!", userRoles);
    }

}
