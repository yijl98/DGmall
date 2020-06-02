package com.dragon.dgmall.manage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan(basePackages = "com.dragon.dgmall.manage.mapper")
public class DgmallManageServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(DgmallManageServiceApplication.class, args);
    }

}
