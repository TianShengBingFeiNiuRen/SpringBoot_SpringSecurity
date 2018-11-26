package com.andon.securitydemo.security.config;

import com.andon.securitydemo.security.bean.SelfUserDetails;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/**
 * 自定义用户认证
 */
@Component
public class SelfUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        SelfUserDetails userInfo = new SelfUserDetails();
        userInfo.setUsername(username); //任意登录用户名

        userInfo.setPassword("123"); //从数据库获取密码

        Set authoritiesSet = new HashSet();
        SimpleGrantedAuthority role_admin = new SimpleGrantedAuthority("ROLE_ADMIN"); //从数据库获取用户角色
        authoritiesSet.add(role_admin);
        userInfo.setAuthorities(authoritiesSet);

        return userInfo;
    }
}
