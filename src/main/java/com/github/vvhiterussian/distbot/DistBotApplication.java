package com.github.vvhiterussian.distbot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DistBotApplication /*implements CommandLineRunner */{

//    @Autowired
//    private UpdatesReceiver updatesReceiver;

    public static void main(String[] args) {
        SpringApplication.run(DistBotApplication.class, args);
    }

//    @Override
//    public void run(String... args) {
//        ExecutorService executorService = Executors.newSingleThreadExecutor();
//        executorService.submit(updatesReceiver);
//    }
}
