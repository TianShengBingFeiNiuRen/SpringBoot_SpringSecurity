package com.nonce.restsecurity.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Andon
 * @date 2019/3/20
 * <p>
 * 登录拦截全局配置
 */
@Configuration
public class WebSecurityConfigure extends WebSecurityConfigurerAdapter {

    @Resource
    private UrlAuthenticationEntryPoint authenticationEntryPoint; //自定义未登录时JSON数据

    @Resource
    private UrlAuthenticationSuccessHandler authenticationSuccessHandler; //自定义登录成功处理器

    @Resource
    private UrlAuthenticationFailureHandler authenticationFailureHandler; //自定义登录失败处理器

    @Resource
    private UrlLogoutSuccessHandler logoutSuccessHandler; //自定义注销成功处理器

    @Resource
    private UrlAccessDeniedHandler accessDeniedHandler; //自定义无权访问处理

    @Resource
    private SelfAuthenticationProvider authenticationProvider; //自定义登录认证

    @Resource
    private SelfFilterInvocationSecurityMetadataSource filterInvocationSecurityMetadataSource; //动态获取url权限配置

    @Resource
    private SelfAccessDecisionManager accessDecisionManager; //权限判断

    @Resource
    private AuthenticationDetailsSource<HttpServletRequest, WebAuthenticationDetails> authenticationDetailsSource; //身份验证详细信息源

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authenticationProvider); //自定义登录认证
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable(); //关闭csrf验证

        http.httpBasic().authenticationEntryPoint(authenticationEntryPoint) //未登录时返回JSON数据

                // 定制请求的授权规则
                .and()
                .authorizeRequests()
                .antMatchers("/connect").permitAll() //无条件允许访问
//                .antMatchers("/security/user/**").hasRole("ADMIN") //需要ADMIN角色才可以访问
                .anyRequest() //其他任何请求
                .authenticated() //都需要身份认证
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O o) {
                        o.setSecurityMetadataSource(filterInvocationSecurityMetadataSource); //动态获取url权限配置
                        o.setAccessDecisionManager(accessDecisionManager); //权限判断
                        return o;
                    }
                })

                // 开启自动配置的登录功能
                .and()
                .formLogin() //开启登录
//                .loginPage("/login") //登录页面(前后端不分离)
                .loginProcessingUrl("/nonceLogin") //自定义登录请求路径(post)
//                .successForwardUrl("/index") //登录成功后的url(post,前后端不分离)
//                .failureForwardUrl("/error") //登录失败后的url(post,前后端不分离)
                .successHandler(authenticationSuccessHandler) //验证成功处理器(前后端分离)
                .failureHandler(authenticationFailureHandler) //验证失败处理器(前后端分离)
                .permitAll()
                .authenticationDetailsSource(authenticationDetailsSource) //身份验证详细信息源

                // 开启自动配置的注销功能
                .and()
                .logout() //用户注销, 清空session
                .logoutUrl("/nonceLogout") //自定义注销请求路径
//                .logoutSuccessUrl("/bye") //注销成功后的url(前后端不分离)
                .logoutSuccessHandler(logoutSuccessHandler) //注销成功处理器(前后端分离)
                .permitAll();

//        http.sessionManagement().invalidSessionUrl("/session/invalid"); //session失效时间

        http.exceptionHandling().accessDeniedHandler(accessDeniedHandler); //无权访问处理器
    }
}
