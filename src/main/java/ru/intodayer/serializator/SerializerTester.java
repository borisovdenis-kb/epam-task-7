package ru.intodayer.serializator;

import java.io.File;


public class SerializerTester {
    private static final String RESOURCES_PATH = "src\\main\\resources\\";

    public static String getAbsoluteFilePath(String relativePath) {
        String workingDir = System.getProperty("user.dir");
        return String.format(
            "%s%s%s%s", workingDir, File.separator, RESOURCES_PATH, relativePath
        );
    }
}
