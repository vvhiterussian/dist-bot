package com.github.vvhiterussian.botservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.github.vvhiterussian.tapi", "com.github.vvhiterussian.botservice" })
public class BotserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BotserviceApplication.class, args);
    }
}
