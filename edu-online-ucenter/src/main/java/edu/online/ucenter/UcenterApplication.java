package edu.online.ucenter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Classname UcenterApplication
 * @Description 用户中心
 * @Date 2020/3/3 13:32
 * @Created by zhoutao
 */
@EnableDiscoveryClient
@SpringBootApplication
@EntityScan(basePackages = {"edu.online.Entity.ucenter"})
@ComponentScan(basePackages = {"edu.online.ucenter"})
public class UcenterApplication {
    public static void main(String[] args) {
        SpringApplication.run(UcenterApplication.class);
    }
}
