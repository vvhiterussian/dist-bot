package com.github.vvhiterussian.botservice.bot;

import com.github.vvhiterussian.tapi.model.Chat;
import org.springframework.core.io.Resource;

public interface Bot {
    void setWebhook(String webhookAddress);
    void sendText(Chat chat, String text);
    void sendPhoto(Chat chat, Resource photo);
    void sendVoice(Chat chat, Resource voice);
}
