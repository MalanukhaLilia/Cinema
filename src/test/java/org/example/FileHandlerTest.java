package org.example;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class FileHandlerTest {
    private FileHelper mockFileHelper;
    private FileHandler fileHandler;
    private List<Session> testSessions;

    @Before
    public void setUp() {
        mockFileHelper = mock(FileHelper.class);
        fileHandler = new FileHandler(mockFileHelper);

        testSessions = new ArrayList<>();
        testSessions.add(new Session("а", (short) 80, LocalDateTime.of(2025, 4, 7, 18, 0), (short) 85, (byte) 2));
        testSessions.add(new Session("б", (short) 115, LocalDateTime.of(2025, 4, 9, 16, 0), (short) 120, (byte) 1));
        testSessions.add(new Session("в", (short) 105, LocalDateTime.of(2025, 4, 8, 20, 0), (short) 110, (byte) 3));
    }

    @Test
    public void exportSessionsToFile_sortsByName() throws IOException {
        fileHandler.exportSessionsToFile(testSessions, "sessions.txt", "назва");

        ArgumentCaptor<List> captor = ArgumentCaptor.forClass(List.class);
        verify(mockFileHelper).writeToFile(captor.capture(), eq("sessions.txt"));

        List<String> writtenLines = captor.getValue();
        assertTrue(writtenLines.get(0).contains("а"));
        assertTrue(writtenLines.get(1).contains("б"));
        assertTrue(writtenLines.get(2).contains("в"));
    }

    @Test
    public void importSessionsFromFile_parsesCorrectly() throws IOException {
        List<String> lines = Arrays.asList(
                "Назва фільму: ОГО",
                "Тривалість фільму: 100 хв",
                "Дата і час сеансу: 03.05.2025 19:30",
                "Тривалість сеансу: 105 хв",
                "Номер залу: 2",
                ""
        );

        when(mockFileHelper.readFromFile("sessions.txt")).thenReturn(lines);

        List<Session> sessions = new ArrayList<>();
        fileHandler.importSessionsFromFile(sessions, "sessions.txt");

        assertEquals(1, sessions.size());
        assertEquals("ОГО", sessions.get(0).getNameOfFilm());
        assertEquals(100, sessions.get(0).getDuration());
        assertEquals(2, sessions.get(0).getHallNumber());
    }

    @Test(expected = IOException.class)
    public void importSessionsFromFile_throwsExceptionIfNullList() throws IOException {
        fileHandler.importSessionsFromFile(null, "sessions.txt");
    }
}
