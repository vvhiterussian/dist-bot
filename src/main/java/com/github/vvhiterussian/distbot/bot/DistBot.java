package com.github.vvhiterussian.distbot.bot;

import com.github.vvhiterussian.distbot.config.DistBotProperties;
import com.github.vvhiterussian.distbot.model.Message;
import com.github.vvhiterussian.distbot.model.Update;
import com.github.vvhiterussian.distbot.tapi.TelegramAPIWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Component
@RestController
public class DistBot extends BotImpl {

    private final DistBotProperties properties;

    @Autowired
    public DistBot(TelegramAPIWrapper wrapper, DistBotProperties properties) {
        super(wrapper);
        this.properties = properties;

        if (properties.isWebhookEnabled()) {
            setWebhook(properties.getWebhookAddress());
        }
    }

    @Override
    public void setWebhook(String webhookAddress) {
        wrapper.setWebhook(webhookAddress);
    }

    @Override
    @PostMapping(path = "/receiveUpdate")
    public void receiveUpdate(@RequestBody Update update) {
        if (update != null && update.getMessage() != null) {
            Message message = update.getMessage();
            sendText(update.getMessage().getChat(), message.getText());
        }
    }
}
