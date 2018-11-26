package com.andon.securitydemo.security.config;

import com.andon.securitydemo.security.bean.UrlResponse;
import com.andon.securitydemo.util.JsonUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 注销成功
 */
@Component
public class UrlLogoutSuccessHandler implements LogoutSuccessHandler {

    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {

        UrlResponse response = new UrlResponse();
        response.setStatus("100");
        response.setMessage("Logout Success!!");

        httpServletResponse.getWriter().write(JsonUtil.getGson().toJson(response));
    }
}
