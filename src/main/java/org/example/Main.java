package org.example;

import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.function.Function;
import java.time.LocalDateTime;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final Cinema cinema = new Cinema();

    public static void main(String[] args) throws IOException {
        seedSessions();
        runMenu();
        exportSessions();
    }

    private static void seedSessions() {
        Session s1 = new Session("Minecraft: Фільм", (short) 101, LocalDateTime.of(2025, 4, 15, 20, 30), (short) 110, (byte) 3);
        s1.createTicketsForSession((short) 10, 120.0);

        Session s2 = new Session("Темний лицар", (short) 152, LocalDateTime.of(2025, 5, 1, 20, 30), (short) 160, (byte) 1);
        s2.createTicketsForSession((short) 8, 150.0);

        Session s3 = new Session("Цікавий фільм", (short) 100, LocalDateTime.of(2025, 4, 12, 17, 15), (short) 110, (byte) 4);
        s3.createTicketsForSession((short) 12, 90.0);

        cinema.addSession(s1);
        cinema.addSession(s2);
        cinema.addSession(s3);
    }

    private static void runMenu() {
        while (true) {
            System.out.println("\n       МЕНЮ    ");
            System.out.println("1. Показати сеанси");
            System.out.println("2. Купити квиток");
            System.out.println("3. Створити новий список сеансів");
            System.out.println("4. Створити квитки для сеансу");
            System.out.println("5. Статистика проданих квитків");
            System.out.println("6. Змінити дані сеансу");
            System.out.println("7. Видалити сеанс");
            System.out.println("8. Видалити квиток");
            System.out.println("9. Експортувати файлу з сеансами");
            System.out.println("0. Вихід");

            System.out.print("Виберіть опцію: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    listSessions();
                    break;
                case 2:
                    buyTicket();
                    break;
                case 3:
                    replaceSessions();
                    break;
                case 4:
                    createTickets();
                    break;
                case 5:
                    cinema.printSoldTicketsStats();
                    break;
                case 6:
                    updateSession();
                    break;
                case 7:
                    deleteSession();
                    break;
                case 8:
                    deleteTicket();
                    break;
                case 9:
                    try {
                        exportSessions();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case 0:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Невірна опція.");
            }

        }
    }

    private static void listSessions() {
        List<Session> sorted = new ArrayList<>(cinema.getSessions());

        sorted.sort(Comparator.comparing(new Function<Session, String>() {
            @Override
            public String apply(Session session) {
                return session.getNameOfFilm();
            }
        }));

        int index = 1;
        for (Session s : sorted) {
            System.out.println(index++ + ". " + s.getNameOfFilm() + " - " + s.getDateAndTimeOfFilm());
        }
    }

    private static void buyTicket() {
        listSessions();
        System.out.print("Оберіть сеанс (номер): ");
        int sessionIndex = scanner.nextInt() - 1;

        Session session = cinema.getSessions().get(sessionIndex);

        session.printAvailableSeats();

        System.out.print("Оберіть місце (номер): ");
        short seat = scanner.nextShort();

        boolean bought = session.buyTicket(seat);
        System.out.println(bought ? "Квиток куплено." : "Місце недоступне або вже продане.");
    }

    private static void replaceSessions() {
        List<Session> newList = new ArrayList<>();

        Session newSession = new Session("Новий фільм", (short) 95, LocalDateTime.now().plusDays(3),
                (short) 100, (byte) 2);
        newSession.createTicketsForSession((short) 5, 100.0);
        newList.add(newSession);

        cinema.setSessions(newList);
        System.out.println("Тепер у списку сеансів лише один новий сеанс.");
    }

    private static void createTickets() {
        listSessions();
        System.out.print("Оберіть сеанс: ");
        int sessionIndex = scanner.nextInt() - 1;

        System.out.print("Кількість місць: ");
        short count = scanner.nextShort();

        System.out.print("Ціна за квиток: ");
        double price = scanner.nextDouble();

        cinema.getSessions().get(sessionIndex).createTicketsForSession(count, price);
        System.out.println("Квитки створено.");
    }

    private static void updateSession() {
        listSessions();
        System.out.print("Оберіть сеанс: ");
        int sessionIndex = scanner.nextInt() - 1;

        Session session = cinema.getSessions().get(sessionIndex);
        scanner.nextLine();

        System.out.print("Нова назва фільму: ");
        session.setNameOfFilm(scanner.nextLine());

        System.out.print("Нова тривалість фільму: ");
        session.setDuration(scanner.nextShort());

        System.out.println("Сеанс оновлено.");
    }

    private static void deleteSession() {
        listSessions();
        System.out.print("Оберіть номер сеансу для видалення: ");
        int index = scanner.nextInt() - 1;

        if (index >= 0 && index < cinema.getSessions().size()) {
            Session toRemove = cinema.getSessions().get(index);
            cinema.removeSession(toRemove);
            System.out.println("Сеанс видалено.");
        } else {
            System.out.println("Невірний номер сеансу.");
        }
    }

    private static void deleteTicket() {
        listSessions();
        System.out.print("Оберіть сеанс: ");
        int sessionIndex = scanner.nextInt() - 1;
        Session session = cinema.getSessions().get(sessionIndex);

        System.out.print("Номер місця для видалення: ");
        short seat = scanner.nextShort();

        boolean removed = session.deleteAvailableTicket(seat) || session.deleteSoldTicket(seat);
        System.out.println(removed ? "Квиток видалено." : "Квиток не знайдено.");
    }

    private static void exportSessions() throws IOException {
        System.out.println("Виберіть критерій сортування перед експортом:");
        System.out.println("1 - За назвою фільму");
        System.out.println("2 - За датою сеансу");
        System.out.println("3 - За номером залу");
        System.out.print("Ваш вибір: ");

        int choice = scanner.nextInt();
        String sortCriterion;

        switch (choice) {
            case 1:
                sortCriterion = "назва";
                break;
            case 2:
                sortCriterion = "дата";
                break;
            case 3:
                sortCriterion = "зал";
                break;
            default:
                System.out.println("Неправильний вибір. Використано сортування за датою.");
                sortCriterion = "дата";
        }

        String filePath = System.getProperty("user.home") + "/Downloads/sessions.txt";
        FileHelper realHelper = new TextFileHelper();
        FileHandler fileHandler = new FileHandler(realHelper);
        fileHandler.exportSessionsToFile(cinema.getSessions(), filePath, sortCriterion);
        System.out.println("Сесії успішно збережено у файл.");
    }
}
