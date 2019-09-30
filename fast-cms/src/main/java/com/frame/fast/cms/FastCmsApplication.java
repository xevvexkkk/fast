package com.frame.fast.cms;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.frame.fast")
@MapperScan("com.frame.fast.repository")
public class FastCmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(FastCmsApplication.class, args);
    }

}
