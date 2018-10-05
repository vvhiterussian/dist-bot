package com.github.vvhiterussian.distbot.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@NoArgsConstructor
@EqualsAndHashCode
@Component
public class File {
    public String fileId;
    public int fileSize;
    public String filePath;
}
