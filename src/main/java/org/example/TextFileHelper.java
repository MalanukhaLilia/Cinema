package org.example;

import java.io.*;
import java.util.*;

public class TextFileHelper implements FileHelper {
    @Override
    public void writeToFile(List<String> lines, String filePath) throws IOException {
        FileWriter writer = new FileWriter(filePath);
        for (String line : lines) {
            writer.write(line + "\n");
        }
        writer.close();
    }

    @Override
    public List<String> readFromFile(String filePath) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        List<String> lines = new ArrayList<>();
        String line;
        while ((line = reader.readLine()) != null) {
            lines.add(line);
        }
        reader.close();
        return lines;
    }
}
