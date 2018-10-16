package com.github.vvhiterussian.botservice.bot;

import com.github.vvhiterussian.tapi.TelegramAPIWrapper;
import com.github.vvhiterussian.tapi.model.Chat;
import com.github.vvhiterussian.tapi.model.Update;
import org.springframework.core.io.Resource;


public abstract class BotImpl implements Bot {

    protected final TelegramAPIWrapper wrapper;
    protected final String token;

    public BotImpl(TelegramAPIWrapper wrapper, String token) {
        this.wrapper = wrapper;
        this.token = token;
    }

    @Override
    public abstract void setWebhook(String webhookAddress);

    public abstract void receiveUpdate(Update update);

    @Override
    public void sendText(Chat chat, String text) {
        wrapper.sendText(chat, text, token);
    }

    @Override
    public void sendPhoto(Chat chat, Resource photo) {
        wrapper.sendPhoto(chat, photo, token);
    }

    @Override
    public void sendVoice(Chat chat, Resource voice) {
        wrapper.sendVoice(chat, voice, token);
    }
}
