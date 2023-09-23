package com.daniu.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

public class ConfigReader {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private final static Logger logger = LoggerFactory.getLogger(ConfigReader.class);

    public static String readValue(String arg) {
        // 读取JSON文件
        logger.info("Loading configuration file");
        ObjectNode payload = objectMapper.createObjectNode();
        try {
            File configFile = new File("src/main/resources/config/config.json");
            payload = (ObjectNode) objectMapper.readTree(configFile);
        } catch (IOException e) {
            logger.error("Config File Not Found");
        }
        logger.info("Configuration file loading completed");
        return payload.findValue(arg).asText();
    }

}
