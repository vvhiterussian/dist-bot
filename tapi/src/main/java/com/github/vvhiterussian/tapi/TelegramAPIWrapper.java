package com.github.vvhiterussian.tapi;

import com.github.vvhiterussian.tapi.model.Chat;
import com.github.vvhiterussian.tapi.model.GetFileResponse;
import com.github.vvhiterussian.tapi.model.PhotoSize;
import com.github.vvhiterussian.tapi.model.Voice;
import com.github.vvhiterussian.tapi.sendable.SendableEntity;
import com.github.vvhiterussian.tapi.sendable.SendableEntityFactory;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Data
@Slf4j
@Component
public class TelegramAPIWrapper {

    private static final String execMethodUrlFormat = "https://api.telegram.org/bot%s/%s";
    private static final String downloadFileUrlFormat = "https://api.telegram.org/file/bot%s/%s";

    private static final String getUpdatesMethod = "getUpdates";
    private static final String setWebhookMethod = "setWebhook";
    private static final String deleteWebhookMethod = "deleteWebhook";
    private static final String getFileMethod = "getFile";

    private static final String sendTextMethod = "sendMessage";

    private final RestTemplate restTemplate;


    @Autowired
    public TelegramAPIWrapper(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void setWebhook(String webhookAddress, String token) {
        deleteWebhook(token);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("url", webhookAddress);
        String response = performGet(getUrl(setWebhookMethod, token), parameters);
    }

    private void deleteWebhook(String token) {
        performGet(getUrl(deleteWebhookMethod, token));
    }

    public byte[] getFile(String fileId, String token) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("file_id", fileId);

        try {
            GetFileResponse response = performGet(getUrl(getFileMethod, token), parameters, GetFileResponse.class);
            if (response != null && response.getResult() != null) {
                String filePath = response.getResult().getFilePath();
                return performGet(getDownloadFileUrl(filePath, token), byte[].class);
            }
        } catch (Exception e) {
            log.error("Error while downloading file", e);
        }
        return null;
    }

    public void sendText(Chat chat, String text, String token) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("chat_id", chat.getId());
        parameters.put("text", text);
        String result = performPost(getUrl(sendTextMethod, token), parameters);
    }

    public void sendPhoto(Chat chat, Resource resource, String token) {
        try {
            send(chat, resource, PhotoSize.class, token);
        } catch (HttpClientErrorException e) {
            log.error("Error posting request", e);
            log.error("Response body {}", e.getResponseBodyAsString());
        } catch (Exception e) {
            log.error("Error sending voice", e);
        }

    }

    public void sendVoice(Chat chat, Resource resource, String token) {
        try {
            send(chat, resource, Voice.class, token);
        } catch (HttpClientErrorException e) {
            log.error("Error posting request", e);
            log.error("Response body {}", e.getResponseBodyAsString());
        } catch (Exception e) {
            log.error("Error sending voice", e);
        }

    }

    public void send(Chat chat, Resource resource, Class clazz, String token) throws Exception {
        SendableEntity sendableEntity = SendableEntityFactory.createSendableEntity(chat, resource, clazz);
        performSend(sendableEntity, token);
    }

    public void performSend(SendableEntity sendableEntity, String token) {
        performPost(getUrl(sendableEntity.getSendMethod(), token), sendableEntity.getMap());
    }

    private String performPost(String url, Map<String, Object> parameters) {
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(parameters);
        ResponseEntity<String> response = restTemplate.postForEntity(url, requestEntity, String.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        }

        return null;
    }

    private String performPost(String url, MultiValueMap<String, Object> body) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(url, requestEntity, String.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        }

        return null;
    }

    private String performGet(String url) {
        return performGet(url, String.class);
    }

    private String performGet(String url, Map<String, Object> parameters) {
        return performGet(url, parameters, String.class);
    }

    private <T> T performGet(String url, Class<T> clazz) {
        return performGet(url, null, clazz);
    }

    private <T> T performGet(String url, Map<String, Object> parameters, Class<T> clazz) {
        StringBuilder urlBuilder = new StringBuilder().append(url);

        if (parameters != null && parameters.size() > 0) {
            boolean isFirst = true;
            for(Map.Entry<String, Object> kv : parameters.entrySet()) {
                urlBuilder.append(isFirst ? "?" : "&").append(String.format("%s=%s", kv.getKey(), kv.getValue()));
                isFirst = false;
            }
        }

        ResponseEntity<T> response = restTemplate.getForEntity(urlBuilder.toString(), clazz);
        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        }

        return null;
    }

    private String getUrl(String method, String token) {
        return String.format(execMethodUrlFormat, token, method);
    }

    private String getDownloadFileUrl(String filePath, String token) {
        return String.format(downloadFileUrlFormat, token, filePath);
    }
}
