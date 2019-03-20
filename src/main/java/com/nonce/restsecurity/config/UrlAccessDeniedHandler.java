package com.nonce.restsecurity.config;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Andon
 * @date 2019/3/20
 * <p>
 * 无权访问
 */
@Component
public class UrlAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {

        /*httpServletResponse.setStatus(300);
        UrlResponse response = new UrlResponse();
        response.setSuccess(false);
        response.setCode("403");
        response.setMessage("Need Authorities!");
        response.setData(null);

        httpServletResponse.getWriter().write(GsonUtil.GSON.toJson(response));*/

        httpServletResponse.sendError(403, "Need Authorities!");
    }
}