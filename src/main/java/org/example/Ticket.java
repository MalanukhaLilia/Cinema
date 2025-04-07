package org.example;

import java.util.Objects;

public class Ticket {
    private double price;
    private short seatNumber;
    private boolean sold;

    public Ticket(short seatNumber, double price) {
        this.seatNumber = seatNumber;
        this.price = price;
        this.sold = false;
    }
    public void setPrice(double price) {
        this.price = price;
    }

    public void setSeatNumber(short seatNumber) {
        this.seatNumber = seatNumber;
    }

    public void setSold(boolean sold) {
        this.sold = sold;
    }

    public short getSeatNumber() { return seatNumber; }
    public double getPrice() { return price; }
    public boolean isSold() { return sold; }

    public void markAsSold() { sold = true; }
    public void markAsAvailable() { sold = false; }

    @Override
    public boolean equals(Object o) {
        boolean result;
        if (this == o) {
            result = true;
        } else if (o == null || getClass() != o.getClass()) {
            result = false;
        } else {
            Ticket ticket = (Ticket) o;
            result = Double.compare(ticket.price, price) == 0 &&
                    seatNumber == ticket.seatNumber &&
                    sold == ticket.sold;
        }
        return result;
    }

    @Override
    public int hashCode() {
        return Objects.hash(price, seatNumber, sold);
    }
}
