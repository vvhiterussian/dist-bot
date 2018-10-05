package com.github.vvhiterussian.distbot;

import com.github.vvhiterussian.distbot.service.UpdatesReceiver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootApplication
public class DistBotApplication implements CommandLineRunner {

    @Autowired
    private UpdatesReceiver updatesReceiver;

    public static void main(String[] args) {
        SpringApplication.run(DistBotApplication.class, args);
    }

    @Override
    public void run(String... args) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(updatesReceiver);
    }
}
