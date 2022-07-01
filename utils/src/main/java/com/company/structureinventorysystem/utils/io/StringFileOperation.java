package com.company.structureinventorysystem.utils.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class StringFileOperation implements FileOperation<String> {

    private static final String NOT_FOUND_PATH_MESSAGE = "Given path %s not found";
    private static final String NULL_OR_EMPTY_PATH_MESSAGE = "Path must not null or empty";
    private static final String LINE_SEPARATOR = System.lineSeparator();

    @Override
    public List<String> read(String path) {
        if (path != null && !path.isEmpty()) {
            Path filePath = Paths.get(path);
            if (Files.exists(filePath)) {
                try {
                    return Files.readAllLines(filePath);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            throw new RuntimeException(String.format(NOT_FOUND_PATH_MESSAGE, path));
        }
        throw new IllegalArgumentException(NULL_OR_EMPTY_PATH_MESSAGE);
    }

    @Override
    public void write(String path, List<String> content) {
        if (path != null && !path.isEmpty() && content != null && content.size() > 0) {
            Path filePath = Paths.get(path);
            String stringSequence = String.join(LINE_SEPARATOR, content);
            try {
                Files.writeString(filePath, stringSequence);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        throw new IllegalArgumentException(NULL_OR_EMPTY_PATH_MESSAGE);
    }

}
