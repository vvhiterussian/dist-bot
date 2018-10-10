package com.github.vvhiterussian.distbot.webhook;

import com.github.vvhiterussian.distbot.model.Update;
import com.github.vvhiterussian.distbot.model.User;
import com.github.vvhiterussian.distbot.model.Voice;
import com.github.vvhiterussian.distbot.tapi.TelegramAPIWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class DistBotController {

    private final TelegramAPIWrapper apiWrapper;

    @Autowired
    DistBotController(TelegramAPIWrapper apiWrapper) {
        this.apiWrapper = apiWrapper;
    }

    @PostMapping(path = "/receiveUpdate")
    public void receiveUpdate(@RequestBody Update update) {
        if (update.getMessage() != null) {
            User from = update.getMessage().getFrom();
            Voice voice = update.getMessage().getVoice();
            if (voice != null && voice.getFileId() != null) {
                log.info("Voice received!");
                byte[] fileData = apiWrapper.getFile(voice.getFileId());
                apiWrapper.sendVoice(from, fileData);
            }
        }
    }

}
