package com.daniu.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Base64;

public class ImageBase64Utils {

    public static String image2Base(String input) throws IOException {
        FileInputStream inputStream = null;
        try {
            Base64.Encoder encoder = Base64.getEncoder();
            inputStream = new FileInputStream(input);
            int available = inputStream.available();
            byte[] bytes = new byte[available];
            inputStream.read(bytes);
            return encoder.encodeToString(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            inputStream.close();
        }
        return null;
    }

}
