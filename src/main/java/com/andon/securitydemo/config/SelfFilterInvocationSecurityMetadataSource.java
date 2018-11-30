package com.andon.securitydemo.config;

import com.andon.securitydemo.service.MenuService;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.*;

/**
 * 动态获取url权限配置
 */
@Component
public class SelfFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    @Resource
    private MenuService menuService;

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {

        Set<ConfigAttribute> set = new HashSet<>();
        // 获取请求地址
        String requestUrl = ((FilterInvocation) o).getRequestUrl();
        Set<String> allUrl = menuService.getAllUrl();
        for (String url : allUrl) {
            if (antPathMatcher.match(url, requestUrl)) {
                Set<String> roleNames = menuService.findRolesByUrl(url); //当前请求需要的权限
                for (String roleName : roleNames) {
                    SecurityConfig securityConfig = new SecurityConfig(roleName);
                    set.add(securityConfig);
                }
            }
        }
        if (ObjectUtils.isEmpty(set)) {
            return SecurityConfig.createList("ROLE_LOGIN");
        }
        return set;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return FilterInvocation.class.isAssignableFrom(aClass);
    }
}
