package com.github.vvhiterussian.distbot.service;

import com.alibaba.fastjson.JSON;
import com.github.vvhiterussian.distbot.config.AppConfig;
import com.github.vvhiterussian.distbot.model.GetFileResponse;
import com.github.vvhiterussian.distbot.model.GetUpdatesResponse;
import com.github.vvhiterussian.distbot.model.Update;
import com.github.vvhiterussian.distbot.model.Voice;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
@Slf4j
public class UpdatesReceiver implements Runnable {

    private static final String execMethodUrlFormat = "https://api.telegram.org/bot%s/%s";
    private static final String getFileUrlFormat = "https://api.telegram.org/file/bot%s/%s";

    private boolean stopFlag = false;
    private final AppConfig config;

    @Autowired
    public UpdatesReceiver(AppConfig config) {
        this.config = config;
    }

    @Override
    public void run() {
        while (!stopFlag) {
            try {
                Thread.sleep(config.getCheckUpdatesPeriod());

                Update[] updates = getUpdates();
                if (updates != null) {
                    for (Update update : getUpdates()) {
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

    public Update[] getUpdates() throws Exception {
        try {
            String response = perform(getMethodUrl("getUpdates"), "GET");
            GetUpdatesResponse getUpdatesResponse = JSON.parseObject(response, GetUpdatesResponse.class);
            return getUpdatesResponse.getResult();
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    public String perform(String urlString, String method) throws Exception {
        try {
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
        } catch (Exception e) {
            log.error(e.getMessage());
            return "";
        }
    }

    public String getMethodUrl(String method) {
        return String.format(execMethodUrlFormat, config.getToken(), method);
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
