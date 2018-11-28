package com.andon.securitydemo.config;

import com.andon.securitydemo.util.JsonUtil;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 无权访问
 */
@Component
public class UrlAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {

        UrlResponse response = new UrlResponse();
        response.setStatus("300");
        response.setMessage("Need Authorities!");

        httpServletResponse.getWriter().write(JsonUtil.getGson().toJson(response));
    }
}
