package com.github.vvhiterussian.distbot.tapi;

import java.io.File;
import java.io.FileOutputStream;

public class FileWriter {

    public static void writeFile(String filePath, byte[] data) throws Exception {
        java.io.File file = new File(filePath);
        FileOutputStream fos = new FileOutputStream(file.getPath());
        fos.write(data);
        fos.close();
    }
}
