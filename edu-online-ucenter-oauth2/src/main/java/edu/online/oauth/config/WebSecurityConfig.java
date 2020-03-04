package edu.online.oauth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @Classname WebSecurityConfig
 * @Description 相当于请求拦截认证
 * @Date 2020/3/3 14:42
 * @Created by zhoutao
 */
@Configuration
@EnableWebSecurity //Spring Security用于启用Web安全的注解
@Order(-1)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    //全局请求忽略规则配置
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/userlogin", "/logout","/getuser");
    }
    //请求拦截 具体的权限控制规则 认证机制配置(默认配置，可自定义)
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic() //httpBasic 认证机制
                    .and()
                .formLogin()//开启formLogin默认配置
                    .and()
                .authorizeRequests()//配置权限
                    .antMatchers( "/login").permitAll() //框架授权时登录的页面不需要认证
                    .anyRequest().authenticated() //所有进入应用的HTTP请求都要进行认证
                    .and()
                .csrf().disable();//禁用csrf
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        AuthenticationManager manager = super.authenticationManagerBean();
        return manager;
    }
}
