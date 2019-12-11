package com.nonce.restsecurity.config;

import com.nonce.restsecurity.service.UserService;
import com.nonce.restsecurity.util.GsonUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * @author Andon
 * @date 2019/3/20
 * <p>
 * 自定义登录成功处理器：返回状态码200
 */
@Component
public class UrlAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Resource
    private UserService userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {

        httpServletResponse.setCharacterEncoding("UTF-8");
        UrlResponse response = new UrlResponse();
        response.setSuccess(true);
        response.setCode("200");
        response.setMessage("Login Success!");

        String username = (String) authentication.getPrincipal(); //表单输入的用户名
        Map<String, Object> userInfo = userService.findMenuInfoByUsername(username, response);

        response.setData(userInfo);

        httpServletResponse.getWriter().write(GsonUtil.GSON.toJson(response));
    }
}
