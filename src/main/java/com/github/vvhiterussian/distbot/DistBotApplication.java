package com.github.vvhiterussian.distbot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class DistBotApplication /*implements CommandLineRunner */{

//    @Autowired
//    private UpdatesReceiver updatesReceiver;

    public static void main(String[] args) throws Exception {
//        try {
//
//
//            File file = new File("tmp/test.ogg");
//            AudioInputStream in = AudioSystem.getAudioInputStream(file);
//            AudioFormat baseFormat = in.getFormat();
//
//            AudioFormat targetFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, baseFormat.getSampleRate(),
//                    16, baseFormat.getChannels(), baseFormat.getChannels() * 2, baseFormat.getSampleRate(), false);
//
//            AudioInputStream pcmIn = AudioSystem.getAudioInputStream(targetFormat, in);
//
//            in.getFormat();
//        } catch (Exception e) {
//            log.error("error", e);
//        }

        SpringApplication.run(DistBotApplication.class, args);
    }

//    @Override
//    public void run(String... args) {
//        ExecutorService executorService = Executors.newSingleThreadExecutor();
//        executorService.submit(updatesReceiver);
//    }
}
