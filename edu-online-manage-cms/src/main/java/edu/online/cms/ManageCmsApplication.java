package edu.online.cms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @Classname ManageCmsApplication
 * @Description 启动类
 * @Date 2019/12/8 19:08
 * @Created by zhoutao
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EntityScan("edu.online.Entity.cms")//扫描实体类
@ComponentScan(basePackages = {"edu.online.api.cms"}) //扫描api对外接口
@ComponentScan(basePackages={"edu.online.cms"})//扫描本项目下的所有类（不写可能404）
@EnableSwagger2
public class ManageCmsApplication {
    public static void main(String[] args) {
        SpringApplication.run(ManageCmsApplication.class);
    }
}