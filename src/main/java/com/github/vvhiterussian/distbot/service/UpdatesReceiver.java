package com.github.vvhiterussian.distbot.service;

import com.alibaba.fastjson.JSON;
import com.github.vvhiterussian.distbot.config.AppConfig;
import com.github.vvhiterussian.distbot.model.GetFileResponse;
import com.github.vvhiterussian.distbot.model.Update;
import com.github.vvhiterussian.distbot.model.Voice;
import com.github.vvhiterussian.distbot.tapi.TelegramAPIWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

//@Service
@Slf4j
public class UpdatesReceiver implements Runnable {

    private static final String execMethodUrlFormat = "https://api.telegram.org/bot%s/%s";
    private static final String getFileUrlFormat = "https://api.telegram.org/file/bot%s/%s";

    private boolean stopFlag = false;

    private final AppConfig config;
    private final TelegramAPIWrapper apiWrapper;

    @Autowired
    public UpdatesReceiver(AppConfig config, TelegramAPIWrapper apiWrapper) {
        this.config = config;
        this.apiWrapper = apiWrapper;
    }

    @Override
    public void run() {
        if (!config.isWebhookEnabled()) {
            while (!stopFlag) {
                try {
                    Thread.sleep(config.getCheckUpdatesPeriod());

                    Update[] updates = apiWrapper.getUpdates();
                    if (updates != null) {
                        for (Update update : updates) {
                            try {
                                Voice voice = update.getMessage().getVoice();
                                if (voice != null) {
                                    GetFileResponse getFileResponse = JSON.parseObject(perform(getFileUrl(voice.getFileId()), "GET").getBytes(), GetFileResponse.class);
                                    perform(downloadFileUrl(getFileResponse.getResult().filePath), "GET");
                                }
                            } catch (Exception e) {
                                log.error("Atomic error", e);
                            }
                        }
                    }
                } catch (Exception e) {
                    log.error(e.getMessage());
                }
            }
        }
    }

    public String perform(String urlString, String method) throws Exception {
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

    public String getFileUrl(String fileId) {
        return String.format("%s?file_id=%s", String.format(execMethodUrlFormat, config.getToken(), "getFile"), fileId);
    }

    public String downloadFileUrl(String filePath) {
        return String.format(getFileUrlFormat, config.getToken(), filePath);
    }

    public void shutdown() {
        stopFlag = true;
    }
}
