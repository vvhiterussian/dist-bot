package com.github.vvhiterussian.distbot.tapi;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@Component
@NoArgsConstructor
@EqualsAndHashCode
public class Response {
    private boolean ok;
    private String description;
    private Object result;
}
