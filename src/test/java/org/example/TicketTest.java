package org.example;

import org.junit.Test;
import static org.junit.Assert.*;

public class TicketTest {
    Ticket ticket = new Ticket((short) 1, 100.0);
    @Test
    public void testTicketCreation() {
        assertEquals(1, ticket.getSeatNumber());
        assertEquals(100.0, ticket.getPrice(), 0.01);
        assertFalse(ticket.isSold());
    }

    @Test
    public void testMarkAsSold() {
        ticket.markAsSold();
        assertTrue(ticket.isSold());
    }

    @Test
    public void testMarkAsAvailable() {
        ticket.markAsSold();
        ticket.markAsAvailable();
        assertFalse(ticket.isSold());
    }
}