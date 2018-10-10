package com.github.vvhiterussian.distbot.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@Component
@EqualsAndHashCode
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Chat {
    private int id;
    private String type;
    private String title;
    private String username;
    private String firstName;
    private String lastName;
    private Boolean allMembersAreAdministrators;
    private ChatPhoto photo;
    private String description;
    private String inviteLink;
    private Message pinnedMessage;
    private String stickerSetName;
    private Boolean canSetStickerSet;
}
