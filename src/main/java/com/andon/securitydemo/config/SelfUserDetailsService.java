package com.andon.securitydemo.config;

import com.andon.securitydemo.domain.Role;
import com.andon.securitydemo.domain.User;
import com.andon.securitydemo.service.UserService;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
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
        if (ObjectUtils.isEmpty(user)) {
            throw new UsernameNotFoundException("User name" + username + "not find!!");
        }
        userInfo.setPassword(user.getPassword()); //从数据库获取密码

        Set<SimpleGrantedAuthority> authoritiesSet = new HashSet<>();
        for (Role role : user.getRoles()) {
            SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(role.getRoleName()); //用户拥有的角色
            authoritiesSet.add(simpleGrantedAuthority);
        }
        userInfo.setAuthorities(authoritiesSet);

        return userInfo;
    }
}
