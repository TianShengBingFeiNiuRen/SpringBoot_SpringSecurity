package com.andon.securitydemo.security;

import com.andon.securitydemo.domain.Menu;
import com.andon.securitydemo.service.UserService;
import com.andon.securitydemo.util.JsonUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

/**
 * 登录成功
 */
@Component
public class UrlAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Resource
    private UserService userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {

        httpServletResponse.setCharacterEncoding("UTF-8");
        UrlResponse response = new UrlResponse();
        response.setStatus("200");
        response.setMessage("Login Success!");

        String username = (String) authentication.getPrincipal(); //表单输入的用户名
        String password = (String) authentication.getCredentials(); //表单输入的密码
        Set<Menu> menus = userService.getMenusByUsername(username);
        response.setResult(menus);

        httpServletResponse.getWriter().write(JsonUtil.getGson().toJson(response));
    }
}
