package edu.online.manage.media.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Classname ManageMediaClientApplication
 * @Description TODO
 * @Date 2020/3/18 14:54
 * @Created by zhoutao
 */
@SpringBootApplication
@ComponentScan(basePackages = {"edu.online.manage.media.client"})
public class ManageMediaClientApplication {
    public static void main(String[] args) {
        SpringApplication.run(ManageMediaClientApplication.class);
    }
}
