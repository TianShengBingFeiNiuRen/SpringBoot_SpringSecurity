package com.nonce.restsecurity.config;

import com.nonce.restsecurity.util.GsonUtil;
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
 * 自定义权限不足处理器：返回状态码403
 */
@Component
public class UrlAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {

        UrlResponse response = new UrlResponse();
        response.setSuccess(false);
        response.setCode("403");
        response.setMessage("Need Authorities!");
        response.setData(null);

        httpServletResponse.setStatus(403);
        httpServletResponse.getWriter().write(GsonUtil.GSON.toJson(response));
    }
}