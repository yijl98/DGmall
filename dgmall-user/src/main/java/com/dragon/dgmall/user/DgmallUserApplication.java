package com.dragon.dgmall.user;

import tk.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.dragon.dgmall.user.mapper")
public class DgmallUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(DgmallUserApplication.class, args);
    }

}
