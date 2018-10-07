package com.github.vvhiterussian.distbot.webhook;

import com.github.vvhiterussian.distbot.model.Update;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class DistBotController {

    @PostMapping(path = "/receiveUpdate")
    public void receiveUpdate(@RequestBody Update update) {
        if (update.getMessage() != null) {
            if (update.getMessage().getVoice() != null) {
                log.info("Voice received!");
            }
        }
    }

}
