package com.andon.securitydemo.security;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 前端交互
 */
@Component
public class SelfAuthenticationProvider implements AuthenticationProvider {

    @Resource
    private SelfUserDetailsService userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String username = (String) authentication.getPrincipal(); //表单输入的用户名
        String password = (String) authentication.getCredentials(); //表单输入的密码

        UserDetails userInfo = userDetailsService.loadUserByUsername(username);

        if (!userInfo.getPassword().equals(password)){
            throw new BadCredentialsException("用户名密码不正确, 请重新登录!");
        }

        return new UsernamePasswordAuthenticationToken(username, password, userInfo.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
