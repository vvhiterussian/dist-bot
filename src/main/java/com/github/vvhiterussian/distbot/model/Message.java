package com.github.vvhiterussian.distbot.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

//TODO different types of message based on deserializing and instanceof
@Data
@Component
@EqualsAndHashCode
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonIgnoreProperties(ignoreUnknown = true)
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

    private MessageType messageType;
    private String fileId;

    public Message() {

    }

    public MessageType getMessageType() {
        if (!StringUtils.isEmpty(this.getText())) {
            messageType = MessageType.TEXT;
        } else if (!StringUtils.isEmpty(this.getPhoto())) {
            messageType = MessageType.PHOTO;
            fileId = this.getPhoto()[0].getFileId();
        } else if (!StringUtils.isEmpty(this.getVoice())) {
            messageType = MessageType.VOICE;
            fileId = this.getVoice().getFileId();
        } else {
            messageType = MessageType.UNKNOWN;
        }
        return messageType;
    }
}
