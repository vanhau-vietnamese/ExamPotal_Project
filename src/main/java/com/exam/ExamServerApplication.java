package com.exam;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ExamServerApplication {
    public static void main(String[] args) {

        SpringApplication.run(ExamServerApplication.class, args);
    }
}
