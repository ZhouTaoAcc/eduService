package edu.online.cms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @Classname ManageCmsApplication
 * @Description 启动类
 * @Date 2019/12/8 19:08
 * @Created by zhoutao
 */
@EnableDiscoveryClient //开启服务发现
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EntityScan("edu.online.Entity.cms")//扫描实体类
@ComponentScan(basePackages = {"edu.online.exception"}) //扫描自定义异常包
@ComponentScan(basePackages = {"edu.online.api.cms"}) //扫描api对外接口
@ComponentScan(basePackages = {"edu.online.cms"})//扫描本项目下的所有类（不写可能404）
@EnableSwagger2
public class ManageCmsApplication {
    public static void main(String[] args) {
        SpringApplication.run(ManageCmsApplication.class);
    }
    /*把RestTemplate注册到容器*/
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate(new OkHttp3ClientHttpRequestFactory());
    }

}
