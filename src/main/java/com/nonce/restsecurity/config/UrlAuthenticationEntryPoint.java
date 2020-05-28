package com.nonce.restsecurity.config;

import com.nonce.restsecurity.util.GsonUtil;
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
 * 自定义未登录时：返回状态码401
 */
@SuppressWarnings("Duplicates")
@Component
public class UrlAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {

        UrlResponse response = new UrlResponse();
        response.setSuccess(false);
        response.setCode("401");
        response.setMessage(e.getMessage());
        response.setData(null);

        httpServletResponse.setStatus(401);
        httpServletResponse.getWriter().write(GsonUtil.GSON.toJson(response));
    }
}
