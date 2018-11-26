package com.andon.securitydemo.security;

import com.andon.securitydemo.util.JsonUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录成功
 */
@Component
public class UrlAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {

        UrlResponse response = new UrlResponse();
        response.setStatus("200");
        response.setStatus("Login Success!");

        httpServletResponse.getWriter().write(JsonUtil.getGson().toJson(response));
    }
}
