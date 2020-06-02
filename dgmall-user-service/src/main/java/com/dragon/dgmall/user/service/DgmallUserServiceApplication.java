package com.dragon.dgmall.user.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan(basePackages = "com.dragon.dgmall.user.mapper")
public class DgmallUserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(DgmallUserServiceApplication.class, args);
    }

}
