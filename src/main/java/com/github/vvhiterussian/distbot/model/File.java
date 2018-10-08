package com.github.vvhiterussian.distbot.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@NoArgsConstructor
@EqualsAndHashCode
@Component
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class File {
    public String fileId;
    public int fileSize;
    public String filePath;
}
