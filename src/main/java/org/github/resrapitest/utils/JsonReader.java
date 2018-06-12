package org.github.resrapitest.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public final class JsonReader {

    public static String read(String filePath) {
        String content = "";

        try {
            content = new String(Files.readAllBytes(Paths.get(filePath)));
        } catch (IOException e) {
            e.printStackTrace();//
        }

        return content;
    }
}
