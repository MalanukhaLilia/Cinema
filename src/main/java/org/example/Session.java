package org.example;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Session {
    private String nameOfFilm;
    private short duration;
    private LocalDateTime dateAndTimeOfFilm;
    private short durationOfSession;
    private byte hallNumber;
    private List<Ticket> availableTickets;
    private List<Ticket> soldTickets;

    public Session(String nameOfFilm, short duration, LocalDateTime dateAndTimeOfFilm,
                   short durationOfSession, byte hallNumber) {
        this.nameOfFilm = nameOfFilm;
        this.duration = duration;
        this.dateAndTimeOfFilm = dateAndTimeOfFilm;
        this.durationOfSession = durationOfSession;
        this.hallNumber = hallNumber;
        this.availableTickets = new ArrayList<>();
        this.soldTickets = new ArrayList<>();
    }
    @Override
    public String toString() {
        String priceInfo;
        if (!availableTickets.isEmpty()) {
            double price = availableTickets.get(0).getPrice(); // беремо ціну першого квитка
            priceInfo = price + " грн";
        } else if (!soldTickets.isEmpty()) {
            double price = soldTickets.get(0).getPrice(); // якщо немає доступних, але є продані
            priceInfo = price + " грн (продано)";
        } else {
            priceInfo = "н/д";
        }

        return "Фільм: " + nameOfFilm +
                ", Тривалість: " + duration +
                " хв, Дата і час: " + dateAndTimeOfFilm +
                ", Ціна: " + priceInfo +
                ", Зал: " + hallNumber;
    }

    public String getNameOfFilm() { return nameOfFilm; }
    public short getDuration() { return duration; }
    public LocalDateTime getDateAndTimeOfFilm() { return dateAndTimeOfFilm; }
    public short getDurationOfSession() { return durationOfSession; }
    public byte getHallNumber() { return hallNumber; }

    public void setNameOfFilm(String nameOfFilm) {
        this.nameOfFilm = nameOfFilm;
    }

    public void setDuration(short duration) {
        this.duration = duration;
    }

    public void setDateAndTimeOfFilm(LocalDateTime dateAndTimeOfFilm) {
        this.dateAndTimeOfFilm = dateAndTimeOfFilm;
    }

    public void setDurationOfSession(short durationOfSession) {
        this.durationOfSession = durationOfSession;
    }

    public void setHallNumber(byte hallNumber) {
        this.hallNumber = hallNumber;
    }

    public void createTicketsForSession(short allSeats, double price) {
        if (allSeats <= 0) {
            throw new IllegalArgumentException("Кількість місць повинна бути більшою за 0");
        }
        if (price < 0) {
            throw new IllegalArgumentException("Ціна не може бути від'ємною");
        }
        for (int i = 1; i <= allSeats; i++) {
            availableTickets.add(new Ticket((short) i, price));
        }
    }

    public boolean buyTicket(short seatNumber) {
        for (Ticket ticket : availableTickets) {
            if (ticket.getSeatNumber() == seatNumber && !ticket.isSold()) {
                ticket.setSold(true);
                soldTickets.add(ticket);
                availableTickets.remove(ticket);
                return true;
            }
        }
        return false;
    }

    public void printAvailableSeats() {
        System.out.println("Доступні місця для сеансу \"" + nameOfFilm + "\":");
        for (Ticket ticket : availableTickets) {
            System.out.println("Місце #" + ticket.getSeatNumber() + " - " + (ticket.isSold() ? "Продано" : "Доступно"));
        }
    }

    public int getSoldTicketsCount() {
        return soldTickets.size();
    }

    public double getTotalIncome() {
        return soldTickets.stream().mapToDouble(Ticket::getPrice).sum();
    }

    public List<Ticket> getAvailableTickets() {
        return new ArrayList<>(availableTickets);
    }

    public boolean deleteAvailableTicket(short seatNumber) {
        return availableTickets.removeIf(ticket -> ticket.getSeatNumber() == seatNumber);
    }

    public boolean deleteSoldTicket(short seatNumber) {
        return soldTickets.removeIf(ticket -> ticket.getSeatNumber() == seatNumber);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Session session = (Session) o;
        return duration == session.duration &&
                durationOfSession == session.durationOfSession &&
                hallNumber == session.hallNumber &&
                Objects.equals(nameOfFilm, session.nameOfFilm) &&
                Objects.equals(dateAndTimeOfFilm, session.dateAndTimeOfFilm);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nameOfFilm, duration, dateAndTimeOfFilm, durationOfSession, hallNumber);
    }
}
