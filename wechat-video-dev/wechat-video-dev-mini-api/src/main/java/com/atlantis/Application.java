package com.atlantis;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Auther: Atlantis
 * @Date: 2019/12/4
 * @Description: SpringBoot引导类
 * @version: 1.0
 */
@SpringBootApplication
@MapperScan(basePackages="com.atlantis.mapper")
@ComponentScan(basePackages= {"com.atlantis", "org.n3r.idworker"})
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
