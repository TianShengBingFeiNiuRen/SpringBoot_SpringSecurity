package com.nonce.restsecurity.config;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Andon
 * @date 2019/3/20
 *
 * 登录失败
 */
@Component
public class UrlAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {

        /*UrlResponse response = new UrlResponse();
        response.setSuccess(false);
        response.setCode("401");
        response.setMessage("Login Failure!");
        response.setData(null);

        httpServletResponse.getWriter().write(GsonUtil.GSON.toJson(response));*/

        httpServletResponse.sendError(401, "Login Failure!");
    }
}
