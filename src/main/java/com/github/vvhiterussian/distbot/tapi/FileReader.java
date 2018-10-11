package com.github.vvhiterussian.distbot.tapi;

import org.springframework.core.io.FileSystemResource;

import java.io.File;
import java.nio.file.Files;

public class FileReader {
    public static FileSystemResource getFileSystemResource(String filePath) throws IllegalArgumentException {
        java.io.File file = new File(filePath);
        if (file.exists()) {
            return new FileSystemResource(file);
        } else {
            throw new IllegalArgumentException("File does not exist");
        }
    }

    public static byte[] getBytes(String filePath) throws Exception {
        java.io.File file = new File(filePath);
        if (file.exists()) {
            return Files.readAllBytes(file.toPath());
        } else {
            throw new IllegalArgumentException("File does not exist");
        }
    }
}
