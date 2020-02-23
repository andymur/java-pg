package com.andymur.pg.hashcode;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class LastAttemptRunner {

    public static final String OUTPUT = "/home/antti/d.out";
    public static final String INPUT = "/home/antti/d_in.txt";
    private static Comparator<Library> libraryComparator = Comparator.comparingDouble(Library::score);
    private static int idGen = 0;
    private static Map<Integer, Book> booksMap;
    private static List<Library> libraries;

    public static void main(String[] args) throws FileNotFoundException {

//        booksMap = getBooksMap();

//        Set<Book> firstLibBooks = getBooks(booksMap, 0, 1, 2, 3, 4);
//
//        Set<Book> secondLibBooks = getBooks(booksMap, 3, 2, 5, 0);
//
//        Library firstLibrary = new Library(nextId(), 2, 2, deadlineCounter);
//        firstLibBooks.forEach(firstLibrary::addBook);
//
//        Library secondLibrary = new Library(nextId(),3, 1, deadlineCounter);
//        secondLibBooks.forEach(secondLibrary::addBook);
//        libraries = new ArrayList<>(Arrays.asList(firstLibrary, secondLibrary));

        DeadlineCounter deadlineCounter = new DeadlineCounter(getDaysTillDeadline());
        hui(deadlineCounter);
        libraries.sort(libraryComparator);
        //System.out.println(libraries);

        List<Library> result = new ArrayList<>();
        System.out.println("Libraries count: " + libraries.size());
        int lnum = 0;
        while (!libraries.isEmpty()) {
            if (lnum % 1000 == 0) {
                System.out.println("Library number " + lnum++);
            }
            Library libraryToSignUp = libraries.remove(libraries.size() - 1);
            libraryToSignUp.signUp();
            if (!libraryToSignUp.books.isEmpty()) {
                result.add(libraryToSignUp);
            }
            libraries.forEach(l -> l.removeBooks(libraryToSignUp.books));
            libraries.sort(libraryComparator);
        }

//        System.out.println(result);

        printLibraries(result);
    }

    private static int nextId() {
        return idGen++;
    }

    private static Set<Book> getBooks(Map<Integer, Book> books, int... bookIds) {
        return Arrays.stream(bookIds).mapToObj(books::get).collect(Collectors.toSet());
    }

    private static int getDaysTillDeadline() {
        return 7;
    }

    private static Map<Integer, Book> getBooksMap() {
        Map<Integer, Book> books = new HashMap<>();

        books.put(0, Book.of(0, 1));
        books.put(1, Book.of(1, 2));
        books.put(2, Book.of(2, 3));
        books.put(3, Book.of(3, 6));
        books.put(4, Book.of(4, 5));
        books.put(5, Book.of(5, 4));
        return books;
    }

    private static class Library {

        private final int id;
        private int signupInDays;
        private Set<Book> books = new HashSet<>();
        private int booksScannedPerDay;
        private int booksScore = 0;
        private final DeadlineCounter deadlineCounter;

        public Library(int id,
                       int signupInDays,
                       int booksScannedPerDay,
                       DeadlineCounter deadlineCounter) {
            this.id = id;
            this.signupInDays = signupInDays;
            this.booksScannedPerDay = booksScannedPerDay;
            this.deadlineCounter = deadlineCounter;
        }

        void addBook(Book book) {
            this.books.add(book);
            booksScore += book.getScore();
        }

        double score() {
            double v = ((double) booksScore / booksScannedPerDay)
                    * (deadlineCounter.getDaysToDeadline() - this.signupInDays);
            return Double.min(v, booksScore);
        }

        public void signUp() {
            deadlineCounter.decreaseDays(signupInDays);
        }

        public void removeBooks(Set<Book> booksToRemove) {
            books.removeAll(booksToRemove);
            revaluate();
        }

        private void revaluate() {
            booksScore = books.stream().mapToInt(Book::getScore).sum();
        }

        @Override
        public String toString() {
            return "Library{" +
                    "signupInDays=" + signupInDays +
                    ", booksScore=" + booksScore +
                    ", booksPerDay=" + booksScannedPerDay +
                    ", score=" + score() +
                    ", booksScannedPerDay=" + booksScannedPerDay +
                    '}';
        }

        public int getId() {
            return id;
        }
    }

    private static class Book {
        final int id;
        final int score;

        public Book(int id, int score) {
            this.id = id;
            this.score = score;
        }

        public static Book of(int id, int score) {
            return new Book(id, score);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Book book = (Book) o;

            return id == book.id;

        }

        @Override
        public int hashCode() {
            return id;
        }

        public int getId() {
            return id;
        }

        public int getScore() {
            return score;
        }
    }

    public static class DeadlineCounter {
        private int daysToDeadline;

        public DeadlineCounter(int daysToDeadline) {
            this.daysToDeadline = daysToDeadline;
        }

        public void decreaseDays(int days) {
            daysToDeadline = daysToDeadline - days;
        }

        public int getDaysToDeadline() {
            return daysToDeadline;
        }

    }

    public static void hui(DeadlineCounter deadlineCounter) throws FileNotFoundException {
        Scanner scanner = new Scanner(new InputStreamReader(new FileInputStream(INPUT)));


        try {
            libraries = new ArrayList<>();

            int booksNum = scanner.nextInt();
            int librariesNum = scanner.nextInt();
            int daysNum = scanner.nextInt();

            scanner.nextLine();
            List<Integer> booksScore = Arrays.asList(readBookScores(scanner, booksNum, deadlineCounter));
            booksMap = new HashMap<>(booksNum);

            for (int i = 0; i < booksNum; i++) {
                booksMap.put(i, new Book(i, booksScore.get(i)));
            }

            /*System.out.println(booksNum);
            System.out.println(librariesNum);
            System.out.println(daysNum);
            System.out.println(booksScore);
            System.out.println(booksMap);
*/
            for (int i = 0; i < librariesNum; i++) {
                Library library = readLibrary(i, scanner, booksMap, deadlineCounter);
                libraries.add(library);
            }
        } finally {
            scanner.close();

        }
    }

    private static void printLibraries(List<Library> libraries) throws FileNotFoundException {
        PrintWriter pw = new PrintWriter(new FileOutputStream(OUTPUT));
        try {
            System.out.println(libraries);
            pw.println(libraries.size());
            Comparator<? super Book> booksComparator = (Comparator<Book>) (b1, b2) -> Integer.compare(b1.getScore(), b2.getScore());
            libraries.forEach(library -> {
                ArrayList<Book> books = new ArrayList<>(library.books);
                books.sort(booksComparator);
                writeLibrary(pw, library, books);
            });
        } finally {
            pw.close();
        }
    }

    private static Integer[] readBookScores(Scanner scanner, int numberOfBooks, DeadlineCounter deadlineCounter) {
        return readNumbers(scanner, numberOfBooks);
    }

    private static Integer[] readBooksForLibrary(Scanner scanner, int numberOfBooks) {
        return readNumbers(scanner, numberOfBooks);
    }

    private static Integer[] readNumbers(Scanner scanner, int count) {
        Integer[] res = new Integer[count];
        for (int i = 0; i < count; i++) {
            res[i] = scanner.nextInt();
        }
        scanner.nextLine();
        return res;
    }

    private static Library readLibrary(int libraryId,
                                       Scanner scanner,
                                       Map<Integer, Book> bookSet,
                                       DeadlineCounter deadlineCounter) {

        int numberOfBooksForLibrary = scanner.nextInt();
        int signupDays = scanner.nextInt();
        int booksInDay = scanner.nextInt();


        Library library = new Library(libraryId, signupDays, booksInDay, deadlineCounter);
        scanner.nextLine();

        List<Integer> bookIds = Arrays.asList(readBooksForLibrary(scanner, numberOfBooksForLibrary));

        List<Book> booksForLibrary = bookIds.stream().map(bookSet::get).collect(Collectors.toList());
        booksForLibrary.forEach(library::addBook);

        return library;
    }

    private static void writeLibrary(PrintWriter printWriter,
                                     Library library,
                                     List<Book> libraryBooks) {
        if (libraryBooks.isEmpty()) {
            return;
        }

        printWriter.println(String.format("%d %d", library.getId(), libraryBooks.size()));
        printWriter.println(libraryBooks.stream().map(book -> Integer.toString(book.getId())).collect(Collectors.joining(" ")));
    }

}
