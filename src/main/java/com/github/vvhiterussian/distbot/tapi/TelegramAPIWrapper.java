package com.github.vvhiterussian.distbot.tapi;

import com.alibaba.fastjson.JSON;
import com.github.vvhiterussian.distbot.config.AppConfig;
import com.github.vvhiterussian.distbot.model.*;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
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

    private static final String sendVoiceMethod = "sendVoice";

    private final RestTemplate restTemplate;
    private final AppConfig config;

    @Autowired
    public TelegramAPIWrapper(RestTemplate restTemplate, AppConfig config) {
        this.restTemplate = restTemplate;
        this.config = config;
        if (config.isWebhookEnabled()) {
            setWebhook(config.getWebhookAddress());
        }
    }

    private void setWebhook(String webhookAddress) {
        deleteWebhook();
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("url", webhookAddress);
        String response = performGet(getUrl(setWebhookMethod), parameters);
    }

    private void deleteWebhook() {
        performGet(getUrl(deleteWebhookMethod));
    }

    public byte[] getFile(String fileId) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("file_id", fileId);

        try {
            GetFileResponse response = performGet(getUrl(getFileMethod), parameters, GetFileResponse.class);
            if (response != null && response.getResult() != null) {
                String filePath = response.getResult().getFilePath();
                return performGet(getDownloadFileUrl(filePath), byte[].class);
            }
        } catch (Exception e) {
            log.error("Error while downloading file", e);
        }
        return null;
    }

    public void resendVoice(User user, Voice voice) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("chat_id", user.getId());
        parameters.put("voice", voice.getFileId());

        String response = performGet(getUrl(sendVoiceMethod), parameters);
    }

    public void sendVoice(User user, byte[] data) {
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("chat_id", user.getId());
        body.add("voice", data);

        String response = performPost(getUrl(sendVoiceMethod), body);
    }

    public Update[] getUpdates() {
        try {
            String response = performGet(getUrl(getUpdatesMethod));
            GetUpdatesResponse getUpdatesResponse = JSON.parseObject(response, GetUpdatesResponse.class);
            return getUpdatesResponse.getResult();
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
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

    private String getUrl(String method) {
        return String.format(execMethodUrlFormat, config.getToken(), method);
    }

    private String getDownloadFileUrl(String filePath) {
        return String.format(downloadFileUrlFormat, config.getToken(), filePath);
    }
}
