package com.nonce.restsecurity.config;

import com.nonce.restsecurity.util.GsonUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Andon
 * @date 2019/3/20
 * <p>
 * 自定义注销成功处理器：返回状态码200
 */
@Component
public class UrlLogoutSuccessHandler implements LogoutSuccessHandler {

    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {

        UrlResponse response = new UrlResponse();
        response.setSuccess(true);
        response.setCode("200");
        response.setMessage("Logout Success!!");
        response.setData(null);

        httpServletResponse.getWriter().write(GsonUtil.GSON.toJson(response));
    }
}
