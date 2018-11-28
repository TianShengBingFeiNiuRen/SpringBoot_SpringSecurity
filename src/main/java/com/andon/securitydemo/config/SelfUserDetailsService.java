package com.andon.securitydemo.config;

import com.andon.securitydemo.domain.User;
import com.andon.securitydemo.service.UserService;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Set;

/**
 * 自定义用户认证
 */
@Component
public class SelfUserDetailsService implements UserDetailsService {

    @Resource
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        SelfUserDetails userInfo = new SelfUserDetails();
        userInfo.setUsername(username); //任意登录用户名

        User user = userService.findByUsername(username);
        userInfo.setPassword(user.getPassword()); //从数据库获取密码

        Set authoritiesSet = new HashSet();
        SimpleGrantedAuthority role_admin = new SimpleGrantedAuthority("ROLE_ADMIN"); //从数据库获取用户角色
        authoritiesSet.add(role_admin);
        userInfo.setAuthorities(authoritiesSet);

        return userInfo;
    }
}
