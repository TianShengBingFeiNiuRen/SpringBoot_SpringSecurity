package com.nonce.restsecurity.config;

import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Andon
 * @date 2019/11/21
 * <p>
 * 自定义身份验证详细信息源
 */
@Component
public class CustomAuthenticationDetailsSource implements AuthenticationDetailsSource<HttpServletRequest, WebAuthenticationDetails> {

    @Override
    public WebAuthenticationDetails buildDetails(HttpServletRequest httpServletRequest) {

        return new CustomWebAuthenticationDetails(httpServletRequest);
    }
}
