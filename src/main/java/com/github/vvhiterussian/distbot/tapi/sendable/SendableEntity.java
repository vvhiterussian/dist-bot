package com.github.vvhiterussian.distbot.tapi.sendable;

import lombok.Getter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public class SendableEntity {

    @Getter
    private String sendMethod;

    @Getter
    private MultiValueMap<String, Object> map;

    public SendableEntity(String sendMethod) {
        this.sendMethod = sendMethod;
        this.map = new LinkedMultiValueMap<>();
    }
}
