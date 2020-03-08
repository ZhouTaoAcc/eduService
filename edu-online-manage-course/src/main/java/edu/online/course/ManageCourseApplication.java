package edu.online.course;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Classname ManageCourseApplication
 * @Description TODO
 * @Date 2020/2/19 14:38
 * @Created by zhoutao
 */
@EnableDiscoveryClient
@EnableFeignClients //开启feign
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EntityScan("edu.online.Entity.course")//扫描实体类
@ComponentScan(basePackages = {"edu.online.exception"}) //扫描自定义异常包
@ComponentScan(basePackages = {"edu.online.api"}) //扫描api对外接口
@ComponentScan(basePackages = {"edu.online.course"})//扫描本服务下面的包
public class ManageCourseApplication {
    public static void main(String[] args) {
        SpringApplication.run(ManageCourseApplication.class);
    }
}
