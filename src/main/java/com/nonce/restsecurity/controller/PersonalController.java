package com.nonce.restsecurity.controller;

import com.nonce.restsecurity.config.UrlResponse;
import com.nonce.restsecurity.service.UserService;
import com.nonce.restsecurity.util.SecurityResponse;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author Andon
 * @date 2019/3/28
 * <p>
 * 个人中心
 */
@SuppressWarnings("Duplicates")
@RestController
@RequestMapping(value = "/common")
public class PersonalController {

    @Resource
    private UserService userService;

    /**
     * 获得用户菜单列表
     */
    @PostMapping(value = "/findPersonalMenuList")
    public UrlResponse findPersonalMenuList(String username) {
        UrlResponse response = new UrlResponse();
        response.setSuccess(false);
        response.setCode("-1");
        response.setMessage("findPersonalMenuList failure!!");
        response.setData("findPersonalMenuList failure!!");
        if (!ObjectUtils.isEmpty(username)) {
            response.setSuccess(true);
            response.setCode("1");
            response.setMessage("findPersonalMenuList Success!");
            Map<String, Object> userInfo = userService.findMenuInfoByUsername(username, response);
            response.setData(userInfo);
        } else {
            response.setMessage("Incomplete information!!");
            response.setData("Incomplete information!!");
        }
        return response;
    }

    /**
     * 获取个人信息
     */
    @PostMapping(value = "/personalUserInfo/get")
    public SecurityResponse personalUserInfoGet(String userId, String username) {
        return userService.personalUserInfoGet(userId, username);
    }

    /**
     * 修改个人信息
     */
    @PostMapping(value = "/personalUserInfo/update")
    public SecurityResponse personalUserInfoUpdate(String nickname, String username, String email, String phone, String password) {
        return userService.personalUserInfoUpdate(nickname, username, email, phone, password);
    }

    /**
     * 修改个人密码
     */
    @PostMapping(value = "/personalPassword/update")
    public SecurityResponse personalPasswordUpdate(String username, String oldPassword, String newPassword) {
        return userService.personalPasswordUpdateByOldPasswordAndNewPassword(username, oldPassword, newPassword);
    }
}
