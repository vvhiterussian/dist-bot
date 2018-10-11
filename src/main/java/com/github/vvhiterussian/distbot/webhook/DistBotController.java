package com.github.vvhiterussian.distbot.webhook;

import com.github.vvhiterussian.distbot.model.Chat;
import com.github.vvhiterussian.distbot.model.Message;
import com.github.vvhiterussian.distbot.model.Update;
import com.github.vvhiterussian.distbot.tapi.FileReader;
import com.github.vvhiterussian.distbot.tapi.FileWriter;
import com.github.vvhiterussian.distbot.tapi.TelegramAPIWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
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
        Message message = update.getMessage();
        if (message != null) {
            log.info("{} message received!", message.getMessageType().toString());
            Chat chat = message.getChat();

            // TODO getMessageMedia; sendText; photo bad encoding
            try {
                String filePath = "/tmp/tmp_file";
                byte[] fileData = apiWrapper.getFile(message.getFileId());
                FileWriter.writeFile(filePath, fileData);

                Resource resource = FileReader.getFileSystemResource(filePath);
                switch (message.getMessageType()) {
                    case TEXT: {
                        // send text message
                    }
                    case PHOTO: {
                        apiWrapper.sendPhoto(chat, resource); break;
                    }
                    case VOICE: {
                        apiWrapper.sendVoice(chat, resource); break;
                    }
                    default: break;
                }
            } catch (Exception e) {

            }
        }
    }

}
