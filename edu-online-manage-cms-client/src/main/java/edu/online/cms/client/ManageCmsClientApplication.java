package edu.online.cms.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Classname ManageCmsClientApplication
 * @Description TODO
 * @Date 2020/2/18 16:33
 * @Created by zhoutao
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EntityScan("edu.online.Entity.cms")//扫描实体类
@ComponentScan(basePackages = {"edu.online.cms"})//扫描本项目下的所有类（不写可能404）
@ComponentScan(basePackages="edu.online.cms.client")
public class ManageCmsClientApplication {
    public static void main(String[] args) {
        SpringApplication.run(ManageCmsClientApplication.class);
    }
}
