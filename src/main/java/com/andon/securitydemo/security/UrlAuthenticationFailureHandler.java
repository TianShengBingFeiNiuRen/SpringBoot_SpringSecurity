package com.andon.securitydemo.security;

import com.andon.securitydemo.util.JsonUtil;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录失败
 */
@Component
public class UrlAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {

        UrlResponse response = new UrlResponse();
        response.setStatus("400");
        response.setMessage("Login Failure!");

        httpServletResponse.getWriter().write(JsonUtil.getGson().toJson(response));
    }
}
