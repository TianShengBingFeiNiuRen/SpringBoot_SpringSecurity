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
import java.util.List;
import java.util.Map;

/**
 * @author Andon
 * @date 2019/3/20
 * <p>
 * 用户管理
 */
@RestController
@RequestMapping(value = "/security")
public class UserController {

    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

    @Resource
    private UserService userService;

    /**
     * 新增用户信息
     */
    @PostMapping(value = "/user/add")
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
    @PostMapping(value = "/user/addRoles")
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
    @PostMapping(value = "/user/delete")
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
     * 修改用户信息
     */
    @PostMapping(value = "/user/update")
    public SecurityResponse update(String id, String nickname, String username, String password, String email, String phone, String validTime, String remark) {
        try {
            if (!ObjectUtils.isEmpty(id) && !ObjectUtils.isEmpty(nickname) && !ObjectUtils.isEmpty(username) && ((ObjectUtils.isEmpty(validTime) && !ObjectUtils.isEmpty(password)) || (!ObjectUtils.isEmpty(validTime) && ObjectUtils.isEmpty(password)))) {
                boolean notExistenceOfUpdateUsername = userService.isNotExistenceOfUpdateUsername(id, username);
                if (notExistenceOfUpdateUsername) {
                    userService.updateUserInfo(id, nickname, username, password, email, phone, validTime, remark);
                    return new SecurityResponse(true, "1", "Update userInfo success!!", "username: " + nickname + " update success!!");
                } else {
                    return new SecurityResponse(false, "-1", "Update userInfo failure!!", "username: " + username + " already exists!!");
                }
            } else {
                return new SecurityResponse(false, "-1", "Incomplete information!!", "Incomplete information!!");
            }
        } catch (Exception e) {
            LOG.error("updateUserInfo failure!! error={}", e.getMessage());
            return new SecurityResponse(false, "-1", "Update userInfo failure!!", "username: " + nickname + " update failure!!");
        }
    }

    /**
     * 获取所有用户信息 (分页加模糊查询)
     */
    @PostMapping(value = "/user/findAll")
    public SecurityResponse findAll(String pageNum, String pageSize, String username, String nickname) {
        try {
            if (!ObjectUtils.isEmpty(pageNum) && !ObjectUtils.isEmpty(pageSize)) {
                SecurityResponse securityResponse = new SecurityResponse();
                List<Map<String, Object>> userInfoList = userService.findAllUserInfo(pageNum, pageSize, username, nickname, securityResponse);
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
