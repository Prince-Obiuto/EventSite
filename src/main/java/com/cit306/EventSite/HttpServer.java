package com.cit306.EventSite;

import com.cit306.EventSite.httpserver.core.ServerListenerThread;
import com.cit306.EventSite.httpserver.config.Config;
import com.cit306.EventSite.httpserver.config.ConfigManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class HttpServer {

    private final static Logger LOGGER = LoggerFactory.getLogger(HttpServer.class);

    public static void main(String[] args) throws IOException {

        LOGGER.info("Starting server...");

        ConfigManager.getInstance().loadConfigFile("src/main/resources/http.json");
        Config config = ConfigManager.getInstance().getCurrentConfig();

        LOGGER.info("Using port " + config.getPort());
        LOGGER.info("Using webroot " + config.getWebRoot());

        try {
            ServerListenerThread serverListenerThread = new ServerListenerThread(config.getPort(), config.getWebRoot());
            serverListenerThread.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
            //TODO Handle later
        }
    }
}
