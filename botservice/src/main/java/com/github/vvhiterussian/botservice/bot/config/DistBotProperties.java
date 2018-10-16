package com.github.vvhiterussian.botservice.bot.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@ConfigurationProperties("app.dist-bot")
@Component
public class DistBotProperties {
    private boolean webhookEnabled;
    private String webhookAddress;
    private String token;
}
