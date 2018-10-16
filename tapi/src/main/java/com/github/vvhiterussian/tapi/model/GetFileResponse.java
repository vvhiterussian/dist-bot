package com.github.vvhiterussian.tapi.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@NoArgsConstructor
@EqualsAndHashCode
@Component
public class GetFileResponse {
    private String ok;
    private File result;
}
