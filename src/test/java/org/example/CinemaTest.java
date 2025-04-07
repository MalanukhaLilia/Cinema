package org.example;

import org.junit.Test;
import static org.junit.Assert.*;

public class CinemaTest {

    Cinema cinema = new Cinema();
    Session session = new Session("Темний лицар", (short) 152, null, (short) 160, (byte) 5);

    @Test
    public void testAddSession() {
        cinema.addSession(session);
        assertEquals(1, cinema.getSessions().size());
    }

    @Test
    public void testRemoveSession() {

        cinema.addSession(session);
        cinema.removeSession(session);
        assertEquals(0, cinema.getSessions().size());
    }

    @Test
    public void testGetSessions() {
        cinema.addSession(session);
        assertTrue(cinema.getSessions().contains(session));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddNullSessionThrowsException() {
        Cinema cinema = new Cinema();
        cinema.addSession(null);
    }
}
