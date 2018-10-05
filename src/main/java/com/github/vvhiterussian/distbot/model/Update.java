package com.github.vvhiterussian.distbot.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@NoArgsConstructor
@Component
@EqualsAndHashCode
public class Update {

    private int updateId;
    private Message message;
    private Message editedMessage;
    private Message channelPost;
    private Message editedChannelPost;

    private InlineQuery inlineQuery;
    private ChosenInlineResult chosenInlineResult;
    private CallbackQuery callbackQuery;
    private ShippingQuery shippingQuery;
    private PreCheckoutQuery preCheckoutQuery;
}
