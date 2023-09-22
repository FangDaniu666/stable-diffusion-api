package com.daniu;

import com.daniu.api.ImageFileUtils;
import com.daniu.api.SdUtils;
import com.daniu.base.BaseQRcode;
import com.fasterxml.jackson.databind.JsonNode;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        String prompt = "masterpiece, best quality, light particle, depth of field, field, scenery, fantasy, blue light, (far away:1.1), no humans, pastel colors, chromatic aberration abuse, glow in the dark, gate, image of unimaginable cosmic terror ripping apart space and time, masterpiece,bestquality,highlydetailed,ultra-detailed,extremely detailed CG unity 8k wallpaper, illustration, lens 135mm, beautiful detailed sky, night, stars, (red plum blossom),((winter)),(((snowflakes))), ((red and white flowers))，(starry sky),(sitting),((colorful)),scenery, lantern,(starfall)";
        String negative_prompt = "watermark, text, error, blurry, jpeg artifacts, cropped, worst quality, low quality, normal quality, jpeg artifacts, signature, watermark\nusername, artist name, (worst quality, low quality:1.4), bad anatomy, ugly, poorly drawn , bad hand, (EasyNegative:1.2),badhandv4,NSFW, (worst quality:2), (low quality:2), (normal quality:2), lowres, normal quality, ((monochrome)), ((grayscale)), skin spots, acnes, skin blemishes, age spot, (ugly:1.331), (duplicate:1.331), (morbid:1.21), (mutilated:1.21), (tranny:1.331), mutated hands, (poorly drawn hands:1.5), blurry, (bad anatomy:1.21), (bad proportions:1.331), extra limbs, (disfigured:1.331), (missing arms:1.331), (extra legs:1.331), (fused fingers:1.61051), (too many fingers:1.61051), (unclear eyes:1.331), lowers, bad hands, missing fingers, extra digit,bad hands, missing fingers, (((extra arms and legs))),lowres, bad anatomy, bad hands, text, error, missing fingers, extra digit, fewer digits, cropped, worst quality, low quality, normal quality, jpeg artifacts, signature, watermark, username, blurry";

        //文生图
        //JsonNode imagesNode = SdUtils.txt2img(prompt, negative_prompt, new File("src/main/resources/txt2img.json"));
        //图生图
        //JsonNode imagesNode = SdUtils.img2img(prompt, negative_prompt,new File("src/main/resources/img2img.json"),"E:\\图片\\220906173918.jpg");

        String downloadPath = "D:\\SD-Webui-Aki\\SDworkspace\\base";
        BaseQRcode.getBaseQRcode("https://www.baidu.com",downloadPath);

        List<File> imageFiles = ImageFileUtils.findImageFiles(downloadPath);
        String baseQRcode = imageFiles.get(0).getAbsolutePath();

        JsonNode imagesNode = SdUtils.generateArtQRCode(prompt, negative_prompt, new File("src/main/resources/qrcode.json"), baseQRcode);


        int imageNum = 0;
        for (JsonNode imageNode : imagesNode) {
            String response = SdUtils.getPngResponse(imageNode);
            System.out.println(response);

            String imageBase64 = imageNode.asText().split(",", 2)[0];
            byte[] imageBytes = org.apache.commons.codec.binary.Base64.decodeBase64(imageBase64.getBytes(StandardCharsets.UTF_8));
            ByteArrayInputStream imageStream = new ByteArrayInputStream(imageBytes);
            BufferedImage image = ImageIO.read(imageStream);
            ImageIO.write(image, "png", new File("output\\output" + imageNum++ + ".png"));
        }
    }
}