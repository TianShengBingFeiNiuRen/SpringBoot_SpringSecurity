package com.nonce.restsecurity.config;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Andon
 * @date 2019/3/20
 * <p>
 * 用户未登录
 */
@Component
public class UrlAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {

        /*UrlResponse response = new UrlResponse();
        response.setSuccess(false);
        response.setCode("401");
        response.setMessage("Need Authorities!");
        response.setData(null);

        httpServletResponse.getWriter().write(GsonUtil.GSON.toJson(response));*/

        httpServletResponse.sendError(401, "Need Authorities!");
    }
}
