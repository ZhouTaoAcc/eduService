package edu.online.oauth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;

/**
 * @Classname UcenterOauthApplication
 * @Description 用户认证服务
 * @Date 2020/3/1 15:35
 * @Created by zhoutao
 */
@EnableFeignClients //开启feign
@EnableDiscoveryClient
@SpringBootApplication
@EntityScan("edu.online.Entity.oauth")//扫描实体类
@ComponentScan(basePackages = {"edu.online.exception"}) //扫描自定义异常包
@ComponentScan(basePackages = {"edu.online.api"}) //扫描api对外接口
@ComponentScan(basePackages = {"edu.online.oauth"})//扫描本服务下面的包
public class UcenterOauthApplication {
    public static void main(String[] args) {
        SpringApplication.run(UcenterOauthApplication.class, args);
    }
    //采用bcrypt对密码进行编码
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate(new OkHttp3ClientHttpRequestFactory());
    }
}
