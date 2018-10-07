package com.github.vvhiterussian.distbot.tapi;

import com.alibaba.fastjson.JSON;
import com.github.vvhiterussian.distbot.config.AppConfig;
import com.github.vvhiterussian.distbot.model.GetUpdatesResponse;
import com.github.vvhiterussian.distbot.model.Update;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@Data
@Slf4j
@Component
public class TelegramAPIWrapper {

    private static final String execMethodUrlFormat = "https://api.telegram.org/bot%s/%s";

    private static final String getUpdatesUrl = "getUpdates";

    private static final String setWebhookUrl = "setWebhook";
    private static final String deleteWebhookUrl = "deleteWebhook";



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

    public void setWebhook(String webhookAddress) {
        deleteWebhook();
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("url", webhookAddress);
        String response = performGet(setWebhookUrl, parameters);
    }

    public void deleteWebhook() {
        performGet(deleteWebhookUrl);
    }

    public Update[] getUpdates() {
        try {
            String response = performGet(getUpdatesUrl);
            GetUpdatesResponse getUpdatesResponse = JSON.parseObject(response, GetUpdatesResponse.class);
            return getUpdatesResponse.getResult();
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    public String performPost(String method, Object data) {
        String url = String.format(execMethodUrlFormat, config.getToken(), method);
        ResponseEntity<String> response = restTemplate.postForEntity(url, data, String.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        }
        return response.getStatusCode().toString();
    }

    public String performGet(String method) {
        String url = String.format(execMethodUrlFormat, config.getToken(), method);
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        }
        return response.getStatusCode().toString();
    }

    public String performGet(String method, Map<String, Object> parameters) {
        if (parameters != null && parameters.size() > 0) {
            boolean isFirst = true;
            StringBuilder sb = new StringBuilder().append(String.format(execMethodUrlFormat, config.getToken(), method));
            for(Map.Entry<String, Object> kv : parameters.entrySet()) {
                sb.append(isFirst ? "?" : "&").append(String.format("%s=%s", kv.getKey(), kv.getValue()));
            }

            ResponseEntity<String> response = restTemplate.getForEntity(sb.toString(), String.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                return response.getBody();
            }
            return response.getStatusCode().toString();
        } else {
            return performGet(method);
        }
    }

    public static String perform(String urlString, String method) throws Exception {
        URL url = new URL(urlString);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod(method);

        int status = con.getResponseCode();

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        con.disconnect();

        return content.toString();
    }

}
