package com.andymur.pg.hashcode;

import java.io.*;
import java.nio.charset.Charset;
import java.util.*;
import java.util.stream.Collectors;

public class ScannerScannerRunner {

    public static void main(String[] args) throws IOException {

        Scanner scanner = new Scanner(new InputStreamReader(new FileInputStream("/home/antti/hashcode.txt")));
        PrintWriter pw = new PrintWriter(new FileOutputStream("/home/antti/hashcode.out"));

        try {
            Set<Library> libraries = new HashSet<>();

            int booksNum = scanner.nextInt();
            int librariesNum = scanner.nextInt();
            int daysNum = scanner.nextInt();

            scanner.nextLine();
            List<Integer> booksScore = Arrays.asList(readBookScores(scanner, booksNum));
            Map<Integer, Book> booksSet = new HashMap<>(booksNum);

            for (int i = 0; i < booksNum; i++) {
                booksSet.put(i, new Book(i, booksScore.get(i)));
            }

            System.out.println(booksNum);
            System.out.println(librariesNum);
            System.out.println(daysNum);
            System.out.println(booksScore);
            System.out.println(booksSet);

            for (int i = 0; i < librariesNum; i++) {
                Library library = readLibrary(i, scanner, booksSet);
                libraries.add(library);
            }

            System.out.println(libraries);
            pw.println(libraries.size());
            libraries.forEach(library -> writeLibrary(pw, library, new ArrayList<>(library.books)));

        } finally {
            scanner.close();
            pw.close();
        }
    }

    private static Integer[] readBookScores(Scanner scanner, int numberOfBooks) {
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
                                       Map<Integer, Book> bookSet) {

        int numberOfBooksForLibrary = scanner.nextInt();
        int signupDays = scanner.nextInt();
        int booksInDay = scanner.nextInt();


        Library library = new Library(libraryId, signupDays, booksInDay);
        scanner.nextLine();

        List<Integer> bookIds = Arrays.asList(readBooksForLibrary(scanner, numberOfBooksForLibrary));

        List<Book> booksForLibrary = bookIds.stream().map(bookSet::get).collect(Collectors.toList());
        booksForLibrary.forEach(library::addBook);

        return library;
    }

    private static void writeLibrary(PrintWriter printWriter,
                                     Library library,
                                     List<Book> libraryBooks) {
        printWriter.println(String.format("%d %d", library.getId(), libraryBooks.size()));
        printWriter.println(libraryBooks.stream().map(book -> Integer.toString(book.getId())).collect(Collectors.joining(", ")));
    }

    private static class Library {
        private int id;
        private int signupInDays;
        private Set<Book> books = new HashSet<>();
        private int booksScannedPerDay;
        private int booksScore = 0;

        public Library(int id,
                       int signupInDays,
                       int booksScannedPerDay) {
            this.id = id;
            this.signupInDays = signupInDays;
            this.booksScannedPerDay = booksScannedPerDay;
        }

        void addBook(Book book) {
            this.books.add(book);
            booksScore += book.getScore();
        }

        double score() {
            return  booksScannedPerDay * ((double) booksScore / this.signupInDays);
        }

        @Override
        public String toString() {
            return "Library{" +
                    "id=" + id +
                    ", signupInDays=" + signupInDays +
                    ", books=" + books +
                    ", booksScannedPerDay=" + booksScannedPerDay +
                    ", booksScore=" + booksScore +
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

        @Override
        public String toString() {
            return "Book{" +
                    "id=" + id +
                    ", score=" + score +
                    '}';
        }
    }
}
