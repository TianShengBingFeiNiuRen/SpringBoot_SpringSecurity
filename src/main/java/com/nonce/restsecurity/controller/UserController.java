package com.nonce.restsecurity.controller;

import com.nonce.restsecurity.service.UserService;
import com.nonce.restsecurity.util.SecurityResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Andon
 * @date 2019/3/20
 * <p>
 * 用户管理
 */
@SuppressWarnings("Duplicates")
@RestController
@RequestMapping(value = "/security-manage")
public class UserController {

    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

    @Resource
    private UserService userService;

    /**
     * 新增用户信息
     */
    @PostMapping(value = "/user-manage/add")
    public SecurityResponse add(String nickname, String username, String password, String email, String phone, String validTime, String remark) {
        if (!ObjectUtils.isEmpty(nickname) && !ObjectUtils.isEmpty(username) && !ObjectUtils.isEmpty(password)) {
            boolean existenceStatus = userService.usernameIsExistence(username);
            if (!existenceStatus) {
                userService.addUserInfo(nickname, username, password, email, phone, validTime, remark);
                return new SecurityResponse(true, "1", "Add user success!!", "username: " + username + " add success!!");
            } else {
                return new SecurityResponse(false, "-1", "User already exists!!", "username: " + username + " already exists!!");
            }
        } else {
            return new SecurityResponse(false, "-1", "Incomplete information!!", "Incomplete information!!");
        }
    }

    /**
     * 为用户分配角色 (批量角色id:逗号分隔)
     */
    @PostMapping(value = "/user-manage/addRoles")
    public SecurityResponse addRolesForUser(String userId, String roleId) {
        try {
            if (!ObjectUtils.isEmpty(userId) && !ObjectUtils.isEmpty(roleId)) {
                userService.addRolesForUser(userId, roleId);
                return new SecurityResponse(true, "1", "User add role success!!", "userId: " + userId + ", roleId: " + roleId);
            } else {
                return new SecurityResponse(false, "-1", "User add role failure!!", "Incomplete information!!");
            }
        } catch (Exception e) {
            LOG.error("addRolesForUser failure!! error={}", e.getMessage());
            return new SecurityResponse(false, "-1", "User add role failure!!", "userId: " + userId + ", roleId: " + roleId);
        }
    }

    /**
     * 删除用户信息 (批量删除id 逗号分隔)
     */
    @PostMapping(value = "/user-manage/delete")
    public SecurityResponse delete(String id) {
        try {
            if (!ObjectUtils.isEmpty(id)) {
                userService.deleteUserInfo(id);
                return new SecurityResponse(true, "1", "Delete user success!!", "id: " + id);
            } else {
                return new SecurityResponse(false, "-1", "Delete user failure!!", "Incomplete information!!");
            }
        } catch (Exception e) {
            LOG.error("deleteUserInfo failure!! error={}", e.getMessage());
            return new SecurityResponse(false, "-1", "Delete user failure!!", "id: " + id);
        }
    }

    /**
     * 修改用户信息(管理员)
     */
    @PostMapping(value = "/user-manage/update")
    public SecurityResponse updateUserInfo(String nickname, String username, String email, String phone, String validTime, String remark) {
        return userService.updateUserInfo(nickname, username, email, phone, validTime, remark);
    }

    /**
     * 修改用户密码(管理员)
     */
    @PostMapping(value = "/user-manage/updatePassword")
    public SecurityResponse updateUserPassword(String userId, String username, String password) {
        return userService.updateUserPassword(userId, username, password);
    }

    /**
     * 获取所有用户信息 (分页加模糊查询)
     */
    @PostMapping(value = "/user-manage/findAll")
    public SecurityResponse findAll(String pageNum, String pageSize, String username, String nickname) {
        try {
            if (!ObjectUtils.isEmpty(pageNum) && !ObjectUtils.isEmpty(pageSize)) {
                SecurityResponse securityResponse = new SecurityResponse();
                userService.findAllUserInfo(pageNum, pageSize, username, nickname, securityResponse);
                return securityResponse;
            } else {
                return new SecurityResponse(false, "-1", "Incomplete information!!", "Incomplete information!!");
            }
        } catch (Exception e) {
            LOG.error("findAllUserInfo failure!! error={}", e.getMessage());
            return new SecurityResponse(false, "-1", "Find all user failure!!", e.getMessage());
        }
    }
}
