package com.andon.securitydemo.security;

import com.andon.securitydemo.util.JsonUtil;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 用户未登录
 */
@Component
public class UrlAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {

        UrlResponse response = new UrlResponse();
        response.setStatus("000");
        response.setMessage("Need Authorities!");

        httpServletResponse.getWriter().write(JsonUtil.getGson().toJson(response));
    }
}
