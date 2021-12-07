package com.fish.flowfront;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@SpringBootApplication
@MapperScan("com.fish.flowfront.mapper")
public class FlowFrontApplication {

    public static void main(String[] args) {
        SpringApplication.run(FlowFrontApplication.class, args);
    }

}
