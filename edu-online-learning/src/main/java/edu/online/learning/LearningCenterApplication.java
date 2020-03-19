package edu.online.learning;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Classname LearningCenterApplication
 * @Description 用户学习中心
 * @Date 2020/3/16 13:14
 * @Created by zhoutao
 */
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
@EntityScan("edu.online.Entity.learning")
@ComponentScan(basePackages = {"edu.online.api.learning"})
@ComponentScan(basePackages = {"edu.online.learning"})
public class LearningCenterApplication {
    public static void main(String[] args) {
        SpringApplication.run(LearningCenterApplication.class);
    }
}
