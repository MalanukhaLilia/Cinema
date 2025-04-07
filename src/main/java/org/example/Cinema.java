package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Cinema {
    private List<Session> sessions;

    public Cinema() {
        this.sessions = new ArrayList<>();
    }

    public void addSession(Session session) {
        if (session == null) { throw new IllegalArgumentException("Сесія не може бути порожньою."); }
        sessions.add(session);
    }

    public void removeSession(Session session) {
        sessions.remove(session);
    }

    public List<Session> getSessions() {
        return sessions;
    }

    public void setSessions(List<Session> sessions) {
        this.sessions = sessions;
    }

    public void printSoldTicketsStats() {
        System.out.println("Статистика проданих квитків:");
        for (Session session : sessions) {
            System.out.println("Фільм: " + session.getNameOfFilm() +
                    ", продано квитків: " + session.getSoldTicketsCount() +
                    ", дохід: " + session.getTotalIncome() + " грн");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cinema)) return false;
        Cinema cinema = (Cinema) o;
        return Objects.equals(sessions, cinema.sessions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sessions);
    }
}
