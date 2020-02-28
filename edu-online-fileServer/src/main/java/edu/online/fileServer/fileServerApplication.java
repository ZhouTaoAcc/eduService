package edu.online.fileServer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Classname fileServerApplication
 * @Description 文件系统 用于文件上传下载服务
 * @Date 2020/2/27 16:09
 * @Created by zhoutao
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EntityScan("edu.online.Entity.fileserver")//扫描实体类
@ComponentScan(basePackages = {"edu.online.fileServer"})
public class fileServerApplication {
    public static void main(String[] args){
        SpringApplication.run(fileServerApplication.class);
    }
}
