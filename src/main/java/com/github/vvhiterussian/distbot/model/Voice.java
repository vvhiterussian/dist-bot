package com.github.vvhiterussian.distbot.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@NoArgsConstructor
@Component
@EqualsAndHashCode
public class Voice {
    private String fileId;
    private int duration;
    private String mimeType;
    private int fileSize;
}
