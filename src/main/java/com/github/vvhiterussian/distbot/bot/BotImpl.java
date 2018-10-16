package com.github.vvhiterussian.distbot.bot;

import com.github.vvhiterussian.distbot.model.Chat;
import com.github.vvhiterussian.distbot.model.Update;
import com.github.vvhiterussian.distbot.tapi.TelegramAPIWrapper;
import org.springframework.core.io.Resource;


public abstract class BotImpl implements Bot {

    protected final TelegramAPIWrapper wrapper;

    public BotImpl(TelegramAPIWrapper wrapper) {
        this.wrapper = wrapper;
    }

    @Override
    public abstract void setWebhook(String webhookAddress);

    public abstract void receiveUpdate(Update update);

    @Override
    public void sendText(Chat chat, String text) {
        wrapper.sendText(chat, text);
    }

    @Override
    public void sendPhoto(Chat chat, Resource photo) {
        wrapper.sendPhoto(chat, photo);
    }

    @Override
    public void sendVoice(Chat chat, Resource voice) {
        wrapper.sendVoice(chat, voice);
    }
}
