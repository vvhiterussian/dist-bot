package com.github.vvhiterussian.distbot.webhook;

import com.github.vvhiterussian.distbot.bot.Bot;
import com.github.vvhiterussian.distbot.bot.DistBot;
import com.github.vvhiterussian.distbot.model.Chat;
import com.github.vvhiterussian.distbot.model.Message;
import com.github.vvhiterussian.distbot.model.Update;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Slf4j
//@RestController
public class DistBotController {

    private final Bot bot;

    @Autowired
    DistBotController(DistBot bot) {
        this.bot = bot;
    }

    @PostMapping(path = "/receiveUpdate")
    public void receiveUpdate(@RequestBody Update update) {
        if (update.getInlineQuery() != null) {

        }

        Message message = update.getMessage();
        if (message != null) {

//            if (message.isCommand()) {
//                if (bot.getCommands().contains(message.getText().replaceFirst("/", ""))) {
//
//                }
//            }

            log.info("{} message received!", message.getMessageType().toString());
            Chat chat = message.getChat();

            // TODO getMessageMedia; sendText; photo bad encoding
            // TODO document type for message
            // TODO inline, commands, etc
            try {
                switch (message.getMessageType()) {
                    case TEXT: {
                        String text = message.getText();
                        bot.sendText(chat, text);
                        break;
                    }
                    case PHOTO: {
//                        String filePath = "/tmp/tmp_file.ogg";
//                        byte[] fileData = bot.getFile(message.getFileId());
//                        FileWriter.writeFile(filePath, fileData);
//                        Resource resource = FileReader.getFileSystemResource(filePath);
//                        apiWrapper.sendPhoto(chat, resource);
                        break;
                    }
                    case VOICE: {
//                        String filePath = "/tmp/tmp_file.ogg";
//                        byte[] fileData = apiWrapper.getFile(message.getFileId());
//                        FileWriter.writeAudio(filePath, fileData);
//                        Resource resource = FileReader.getFileSystemResource(filePath);
//                        apiWrapper.sendVoice(chat, resource);
                        break;
                    }
                    case DOCUMENT: {
                        break;
                    }
                    default: break;
                }
            } catch (Exception e) {
                log.error("Error sending reply", e);
            }
        }
    }

}
