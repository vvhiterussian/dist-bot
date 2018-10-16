package com.github.vvhiterussian.tapi.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.github.vvhiterussian.tapi.model.inline.InlineQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@NoArgsConstructor
@Component
@EqualsAndHashCode
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
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
