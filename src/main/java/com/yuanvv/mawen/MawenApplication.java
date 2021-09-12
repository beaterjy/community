package com.yuanvv.mawen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MawenApplication {

    public static void main(String[] args) {
        SpringApplication.run(MawenApplication.class, args);
    }

}
