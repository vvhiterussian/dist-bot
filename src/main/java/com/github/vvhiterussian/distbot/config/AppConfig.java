package com.github.vvhiterussian.distbot.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@ConfigurationProperties("app")
@Component
public class AppConfig {
    private String token;
    private long checkUpdatesPeriod;
}
