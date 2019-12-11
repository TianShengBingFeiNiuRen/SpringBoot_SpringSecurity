package com.nonce.restsecurity.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
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
    private UrlAuthenticationEntryPoint authenticationEntryPoint; //自定义未登录时：返回状态码401

    @Resource
    private UrlAuthenticationSuccessHandler authenticationSuccessHandler; //自定义登录成功处理器：返回状态码200

    @Resource
    private UrlAuthenticationFailureHandler authenticationFailureHandler; //自定义登录失败处理器：返回状态码402

    @Resource
    private UrlAccessDeniedHandler accessDeniedHandler; //自定义权限不足处理器：返回状态码403

    @Resource
    private UrlLogoutSuccessHandler logoutSuccessHandler; //自定义注销成功处理器：返回状态码200

    @Resource
    private SelfAuthenticationProvider authenticationProvider; //自定义登录认证

    @Resource
    private SelfFilterInvocationSecurityMetadataSource filterInvocationSecurityMetadataSource; //动态获取url权限配置

    @Resource
    private SelfAccessDecisionManager accessDecisionManager; //自定义权限判断管理器

    @Resource
    private AuthenticationDetailsSource<HttpServletRequest, WebAuthenticationDetails> authenticationDetailsSource; //身份验证详细信息源

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/common/**"); //无条件允许访问
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authenticationProvider); //自定义登录认证
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // 关闭csrf验证(防止跨站请求伪造攻击)
        http.csrf().disable();

        // 未登录时：返回状态码401
        http.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint);

        // 无权访问时：返回状态码403
        http.exceptionHandling().accessDeniedHandler(accessDeniedHandler);

        // url权限认证处理
        http.antMatcher("/**").authorizeRequests()
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
                });

        // 将session策略设置为无状态的,通过token进行权限认证
//        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // 开启自动配置的登录功能
        http.formLogin() //开启登录
//                .loginPage("/login") //登录页面(前后端不分离)
                .loginProcessingUrl("/nonceLogin") //自定义登录请求路径(post)
//                .successForwardUrl("/index") //登录成功后的url(post,前后端不分离)
//                .failureForwardUrl("/error") //登录失败后的url(post,前后端不分离)
                .successHandler(authenticationSuccessHandler) //验证成功处理器(前后端分离)：返回状态码200
                .failureHandler(authenticationFailureHandler) //验证失败处理器(前后端分离)：返回状态码402
                .authenticationDetailsSource(authenticationDetailsSource); //身份验证详细信息源(登录验证中增加额外字段)

        // 开启自动配置的注销功能
        http.logout() //用户注销, 清空session
                .logoutUrl("/nonceLogout") //自定义注销请求路径
//                .logoutSuccessUrl("/bye") //注销成功后的url(前后端不分离)
                .logoutSuccessHandler(logoutSuccessHandler); //注销成功处理器(前后端分离)：返回状态码200
    }
}
