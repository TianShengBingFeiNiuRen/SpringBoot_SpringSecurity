package com.nonce.restsecurity.config;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author Andon
 * @date 2019/3/20
 * <p>
 * 自定义登录认证
 */
@Slf4j
@Component
public class SelfAuthenticationProvider implements AuthenticationProvider {

    @Resource
    private SelfUserDetailsService selfUserDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        log.info("authentication >> {}", JSONObject.toJSONString(authentication, SerializerFeature.WriteMapNullValue));
        CustomWebAuthenticationDetails customWebAuthenticationDetails = (CustomWebAuthenticationDetails) authentication.getDetails(); //获取身份验证详细信息
//        String remoteAddress = customWebAuthenticationDetails.getRemoteAddress();
//        String sessionId = customWebAuthenticationDetails.getSessionId();
//        System.out.println("remoteAddress >> " + remoteAddress);
//        System.out.println("sessionId >> " + sessionId);
//        System.out.println("details >> " + JSONObject.toJSONString(customWebAuthenticationDetails, SerializerFeature.WriteMapNullValue));
        System.out.println("macAddress >> " + customWebAuthenticationDetails.getMacAddress()); //用于校验mac地址白名单(这里只是打个比方，登录验证中增加的额外字段)

        String username = (String) authentication.getPrincipal(); //表单输入的用户名
        String password = (String) authentication.getCredentials(); //表单输入的密码

        UserDetails userInfo = selfUserDetailsService.loadUserByUsername(username);

        boolean matches = new BCryptPasswordEncoder().matches(password, userInfo.getPassword()); //校验用户名密码
        if (!matches) {
            throw new BadCredentialsException("The password is incorrect!!");
        }
        return new UsernamePasswordAuthenticationToken(username, userInfo.getPassword(), userInfo.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
