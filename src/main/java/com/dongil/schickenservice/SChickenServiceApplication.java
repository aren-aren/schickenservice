package com.dongil.schickenservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class SChickenServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SChickenServiceApplication.class, args);
    }

}
