package com.github.vvhiterussian.distbot.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@NoArgsConstructor
@Component
@EqualsAndHashCode
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Message {

    private int messageId;
    private User from;
    private int date;
    private Chat chat;
    private User forwardFrom;
    private Chat forwardFromChat;
    private int forwardFromMessageId;
    private String forwardSignature;
    private int forwardDate;
    private Message replyToMessage;
    private int editDate;
    private String mediaGroupId;
    private String authorSignature;
    private String text;
    private MessageEntity[] entities;
    private MessageEntity[] captionEntities;
    private Audio audio;
    private Document document;
    private Animation animation;
    private Game game;
    private PhotoSize[] photo;
    private Sticker sticker;
    private Video video;
    private Voice voice;
    private VideoNote videoNote;
    private String caption;
    private Contact contact;
    private Location location;
    private Venue venue;
    private User[] newChatMembers;
    private User leftChatMember;
    private String newChatTitle;
    private PhotoSize[] newChatPhoto;
    private boolean deleteChatPhoto;
    private boolean groupChatCreated;
    private boolean supergroupChatCreated;
    private boolean channelChatCreated;
    private int migrateToChatId;
    private int migrateFromChatId;
    private Message pinnedMessage;
    private Invoice invoice;
    private SuccessfulPayment successfulPayment;
    private String connectedWebsite;
    private PassportData passportData;


}
