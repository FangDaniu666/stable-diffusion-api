package com.daniu.api;

import com.daniu.base.BaseQRcode;
import com.daniu.utils.ImageBase64Utils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import okhttp3.*;
import org.jetbrains.annotations.Nullable;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class SdUtils {
    private final static Logger logger = LoggerFactory.getLogger(SdUtils.class);
    private static final String url = "http://127.0.0.1:7861";  //sd地址
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private static final OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(300, TimeUnit.SECONDS)
            .readTimeout(300, TimeUnit.SECONDS)
            .writeTimeout(300, TimeUnit.SECONDS)
            .build();

    public static JsonNode txt2img(String prompt, String negative_prompt, File file) {
        ObjectNode payload = objectMapper.createObjectNode();
        try {
            payload = (ObjectNode) objectMapper.readTree(file);
        } catch (IOException e) {
            logger.error("File Not Found");
        }
        payload.put("prompt", prompt);
        payload.put("negative_prompt", negative_prompt);
        logger.info("Generating image");

        return getJsonNode(payload);
    }

    public static JsonNode img2img(String prompt, String negative_prompt, File file, String img) {
        ObjectNode payload = objectMapper.createObjectNode();
        try {
            payload = (ObjectNode) objectMapper.readTree(file);
        } catch (IOException e) {
        }
        payload.put("prompt", prompt);
        payload.put("negative_prompt", negative_prompt);

        logger.info("Setting init image");
        String base = null;
        try {
            base = ImageBase64Utils.image2Base(img);
        } catch (IOException e) {
            logger.error("init_images is null");
        }
        ArrayNode initImages = objectMapper.createArrayNode();
        initImages.add(base);
        payload.set("init_images", initImages);

        logger.info("Generating image");
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), payload.toString());
        Request request = new Request.Builder()
                .url(url + "/sdapi/v1/img2img")
                //.addHeader("Authorization", "Basic RmFuZ0Rhbml1OkZhbmdEYW5pdTY2Ng==")
                .post(requestBody)
                .build();

        return getResponse(request);
    }

    public static JsonNode generateArtQRCode(String prompt, String negative_prompt, File file, String baseQRcode) {
        ObjectNode payload = objectMapper.createObjectNode();
        try {
            payload = (ObjectNode) objectMapper.readTree(file);
        } catch (IOException e) {
            logger.error("File Not Found");
        }
        payload.put("prompt", prompt);
        payload.put("negative_prompt", negative_prompt);

        String base = null;
        try {
            base = ImageBase64Utils.image2Base(baseQRcode);
            if (base != null) {
                ImageFileUtils.deleteImageFiles(new File(baseQRcode).getParent());
            }
        } catch (IOException e) {
            logger.error("Base QRcode Not Found");
        }
        ((ObjectNode) payload.at("/alwayson_scripts/controlnet/args/0")).put("input_image", base);
        ((ObjectNode) payload.at("/alwayson_scripts/controlnet/args/1")).put("input_image", base);
        logger.info("Generating QRcode");

        return getJsonNode(payload);
    }

    public static String getPngResponse(JsonNode imageNode) {
        JSONObject pngPayload = new JSONObject();
        pngPayload.put("image", "data:image/png;base64," + imageNode.asText());

        RequestBody pngRequestBody = RequestBody.create(MediaType.parse("application/json"), pngPayload.toString());
        Request pngRequest = new Request.Builder()
                .url(url + "/sdapi/v1/png-info")
                //.addHeader("Authorization", "Basic RmFuZ0Rhbml1OkZhbmdEYW5pdTY2Ng==")
                .post(pngRequestBody)
                .build();

        Response pngResponse;
        String parameters;
        try {
            pngResponse = client.newCall(pngRequest).execute();
            String pngResponseBody = pngResponse.body().string();
            JSONObject pngInfo = new JSONObject(pngResponseBody);
            parameters = pngInfo.optString("info");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return parameters;
    }

    @Nullable
    private static JsonNode getResponse(Request request) {
        Response response;
        try {
            response = client.newCall(request).execute();
            String responseBody = response.body().string();
            JsonNode jsonNode = objectMapper.readTree(responseBody);
            return jsonNode.get("images");
        } catch (IOException e) {
            e.getMessage();
            logger.error("images is null");
        }
        return null;
    }

    @Nullable
    private static JsonNode getJsonNode(ObjectNode payload) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), payload.toString());
        Request request = new Request.Builder()
                .url(url + "/sdapi/v1/txt2img")
                //.addHeader("Authorization", "Basic RmFuZ0Rhbml1OkZhbmdEYW5pdTY2Ng==")
                .post(requestBody)
                .build();

        return getResponse(request);
    }
}