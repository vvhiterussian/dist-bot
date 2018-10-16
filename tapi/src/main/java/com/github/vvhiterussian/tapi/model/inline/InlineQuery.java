package com.github.vvhiterussian.tapi.model.inline;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.github.vvhiterussian.tapi.model.Location;
import com.github.vvhiterussian.tapi.model.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@NoArgsConstructor
@Component
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class InlineQuery {
    private String id;
    private User from;
    private Location location;
    private String query;
    private String offset;
}
