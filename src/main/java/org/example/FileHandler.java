package org.example;

import java.time.LocalDateTime;
import java.io.IOException;
import java.util.*;

public class FileHandler {
    private final FileHelper fileHelper;

    public FileHandler(FileHelper helper) {
        this.fileHelper = helper;
    }

    public void exportSessionsToFile(List<Session> sessions, String filePath, String sortBy) throws IOException {
        List<Session> list = new ArrayList<>(sessions);

        if (sortBy.equals("назва")) {
            list.sort((a, b) -> a.getNameOfFilm().compareToIgnoreCase(b.getNameOfFilm()));
        } else if (sortBy.equals("дата")) {
            list.sort((a, b) -> a.getDateAndTimeOfFilm().compareTo(b.getDateAndTimeOfFilm()));
        } else if (sortBy.equals("зал")) {
            list.sort((a, b) -> Byte.compare(a.getHallNumber(), b.getHallNumber()));
        }

        List<String> lines = new ArrayList<>();
        for (Session s : list) {
            lines.add(s.toString());
        }

        fileHelper.writeToFile(lines, filePath);
    }

    public void importSessionsFromFile(List<Session> sessions, String filePath) throws IOException {
        if (sessions == null) throw new IOException("Сесії не були додані.");

        List<String> lines = fileHelper.readFromFile(filePath);

        for (String line : lines) {
            String[] parts = line.split(", ");
            if (parts.length < 5) continue;

            String name = parts[0].replace("Фільм: ", "");
            short duration = Short.parseShort(parts[1].replaceAll("\\D+", ""));
            LocalDateTime date = LocalDateTime.parse(
                    parts[2].replace("Дата і час: ", ""),
                    java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm")
            );
            byte hall = Byte.parseByte(parts[4].replaceAll("\\D+", ""));
            short durationOfSession = 100;
            sessions.add(new Session(name, duration, date, durationOfSession, hall));
        }
    }
}
