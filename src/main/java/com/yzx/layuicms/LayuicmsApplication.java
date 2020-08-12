package com.yzx.layuicms;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.yzx.layuicms.mapper")
@SpringBootApplication
public class LayuicmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(LayuicmsApplication.class, args);
    }

}
