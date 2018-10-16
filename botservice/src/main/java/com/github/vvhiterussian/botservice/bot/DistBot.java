package com.github.vvhiterussian.botservice.bot;

import com.github.vvhiterussian.botservice.bot.config.DistBotProperties;
import com.github.vvhiterussian.tapi.TelegramAPIWrapper;
import com.github.vvhiterussian.tapi.model.Message;
import com.github.vvhiterussian.tapi.model.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Component
@RestController
public class DistBot extends BotImpl {

    @Autowired
    public DistBot(TelegramAPIWrapper wrapper, DistBotProperties properties) {
        super(wrapper, properties.getToken());
        if (properties.isWebhookEnabled()) {
            setWebhook(properties.getWebhookAddress());
        }
    }

    @Override
    public void setWebhook(String webhookAddress) {
        wrapper.setWebhook(webhookAddress, token);
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
