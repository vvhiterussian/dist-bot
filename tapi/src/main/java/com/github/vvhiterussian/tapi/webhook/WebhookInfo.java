package com.github.vvhiterussian.tapi.webhook;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@Component
@EqualsAndHashCode
@NoArgsConstructor
public class WebhookInfo {
    private String url;
    private boolean hasCustomCertificate;
    private int pendingUpdateCount;
    private int lastErrorDate;
    private String lastErrorMessage;
    private int maxConnections;
    private String[] allowedUpdates;
}
