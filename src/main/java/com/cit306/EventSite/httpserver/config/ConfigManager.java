package com.cit306.EventSite.httpserver.config;

import com.cit306.EventSite.httpserver.util.Json;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ConfigManager {

    private static ConfigManager myConfigManager;
    private static Config myCurrentConfig;

    private ConfigManager(){
    }
    public static ConfigManager getInstance(){
        if (myConfigManager == null){
            myConfigManager = new ConfigManager();
        }
        return myConfigManager;
    }

    // Used to load a config file by file path
    public void loadConfigFile(String filePath) throws IOException {
        FileReader fileReader = null;
        try {
            fileReader = new FileReader(filePath);
        } catch (FileNotFoundException e) {
            throw new HttpConfigException(e);
        }
        StringBuffer stringBuffer = new StringBuffer();
        int i;
        try {
            while((i = fileReader.read()) != -1){
                stringBuffer.append((char) i);
            }
        } catch (IOException e) {
            throw new HttpConfigException(e);
        }
        JsonNode config = null;
        try {
            config = Json.parse(stringBuffer.toString());
        } catch (JsonProcessingException e) {
            throw new HttpConfigException("Error parsing config file", e);
        }
        try {
            myCurrentConfig = Json.fromJson(config, Config.class);
        } catch (JsonProcessingException e) {
            throw new HttpConfigException("Error parsing the config file internal", e);
        }
    }

    // Returns the current loaded config
    public Config getCurrentConfig(){
        if(myCurrentConfig == null){
            throw new HttpConfigException("No current configuration set");
        }
        return myCurrentConfig;
    }
}
