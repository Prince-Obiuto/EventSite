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

        System.out.println("Starting server...");

        ConfigManager.getInstance().loadConfigFile("src/main/resources/http.json");
        Config config = ConfigManager.getInstance().getCurrentConfig();

        System.out.println("Using port " + config.getPort());
        System.out.println("Using webroot " + config.getWebroot());

        try {
            ServerListenerThread serverListenerThread = new ServerListenerThread(config.getPort(), config.getWebroot());
            serverListenerThread.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
            //TODO Handle later
        }
    }
}
