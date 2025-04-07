package org.example;

import java.io.IOException;
import java.util.List;

public interface FileHelper {
    void writeToFile(List<String> lines, String filePath) throws IOException;
    List<String> readFromFile(String filePath) throws IOException;
}
