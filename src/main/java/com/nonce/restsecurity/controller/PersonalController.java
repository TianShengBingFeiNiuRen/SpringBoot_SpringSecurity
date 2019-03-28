package com.nonce.restsecurity.controller;

import com.nonce.restsecurity.config.UrlResponse;
import com.nonce.restsecurity.service.UserService;
import com.nonce.restsecurity.util.SecurityResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger LOG = LoggerFactory.getLogger(PersonalController.class);

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
     * 修改个人信息
     */
    @PostMapping(value = "/personalUserInfo/update")
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
}
