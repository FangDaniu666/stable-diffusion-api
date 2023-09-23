package com.daniu.api;

import com.daniu.utils.ConfigReader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ImageFileUtils {
    public static List<File> findImageFiles() {
        List<File> imageFiles = new ArrayList<>();
        File baseDir = new File(ConfigReader.readValue("downloadPath"));

        if (baseDir.exists() && baseDir.isDirectory()) {
            findImageFilesRecursive(baseDir, imageFiles);
        }

        return imageFiles;
    }

    public static void deleteImageFiles() {
        List<File> imageFiles = findImageFiles();

        for (File file : imageFiles) {
            file.delete();
        }
    }

    private static void findImageFilesRecursive(File dir, List<File> imageFiles) {
        File[] files = dir.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    findImageFilesRecursive(file, imageFiles);
                } else if (isImageFile(file)) {
                    imageFiles.add(file);
                }
            }
        }
    }

    private static boolean isImageFile(File file) {
        String fileName = file.getName().toLowerCase();
        return fileName.endsWith(".jpg") || fileName.endsWith(".jpeg") ||
                fileName.endsWith(".png") || fileName.endsWith(".gif");
    }
}
