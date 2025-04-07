package org.example;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.LocalDateTime;

public class SessionTest {

    Session session = new Session("Темний лицар", (short) 152, LocalDateTime.of(2025, 5, 1, 20, 30), (short) 160, (byte) 5);

    @Test
    public void testCreateSession() {
        assertEquals("Темний лицар", session.getNameOfFilm());
        assertEquals(152, session.getDuration());
        assertEquals(LocalDateTime.of(2025, 5, 1, 20, 30), session.getDateAndTimeOfFilm());
        assertEquals(160, session.getDurationOfSession());
        assertEquals(5, session.getHallNumber());
    }

    @Test
    public void testCreateTickets() {
        Session session = new Session("Темний лицар", (short) 152, LocalDateTime.of(2025, 5, 1, 20, 30), (short) 160, (byte) 5);
        session.createTicketsForSession((short) 5, 100.0);

        assertEquals(5, session.getAvailableTickets().size()); // Перевіряємо доступні квитки
    }

    @Test
    public void testBuyTicket() {
        session.createTicketsForSession((short) 5, 100.0);
        boolean success = session.buyTicket((short) 3);
        assertTrue(success);
        assertEquals(1, session.getSoldTicketsCount());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateTicketsForSessionWithNegativePriceThrowsException() {
        Session session = new Session("г", (short) 90, LocalDateTime.now(),
                (short) 95, (byte) 1);
        session.createTicketsForSession((short) 10, -50.0);
    }
}