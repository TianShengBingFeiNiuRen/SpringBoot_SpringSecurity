package com.nonce.restsecurity.config;

import com.nonce.restsecurity.service.UserService;
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
 * @author Andon
 * @date 2019/3/20
 * <p>
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

        String password = userService.findPasswordByUsernameAfterValidTime(username);
        if (ObjectUtils.isEmpty(password)) {
            throw new UsernameNotFoundException("User name" + username + "not find!!");
        }
        userInfo.setPassword(password); //从数据库获取密码

        Set<SimpleGrantedAuthority> authoritiesSet = new HashSet<>();
        List<String> roles = userService.findRoleNameByUsername(username);
        for (String roleName : roles) {
            SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(roleName); //用户拥有的角色
            authoritiesSet.add(simpleGrantedAuthority);
        }
        userInfo.setAuthorities(authoritiesSet);

        return userInfo;
    }
}
