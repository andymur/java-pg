package com.andymur.pg.hashcode;

import java.util.*;

public class BookScannerRunner {

    public static void main(String[] args) {

        Map<Integer, Book> books = new HashMap<>();

        books.put(0, Book.of(0, 1));
        books.put(1, Book.of(1, 2));
        books.put(2, Book.of(2, 3));
        books.put(3, Book.of(3, 6));
        books.put(4, Book.of(4, 5));
        books.put(5, Book.of(5, 4));

        Set<Book> firstLibBooks =
                new HashSet<Book>(Arrays.asList(books.get(0), books.get(1), books.get(2), books.get(3),
                        books.get(4)));

        // 3 2 5  0

        Set<Book> secondLibBooks =
                new HashSet<>(Arrays.asList(books.get(3), books.get(2), books.get(5), books.get(0)));

        Library firstLibrary = new Library(2, 2);

        firstLibBooks.forEach(firstLibrary::addBook);

        Library secondLibrary = new Library(3, 1);

        secondLibBooks.forEach(secondLibrary::addBook);

        Set<Library> libraries = new TreeSet<>(Comparator.comparingDouble(Library::score).reversed());

        libraries.addAll(Arrays.asList(firstLibrary, secondLibrary));

        Optional<Library> first = libraries.stream().findFirst();
        System.out.println(first);
        System.out.println(libraries);
    }

    private static class Library {

        private int signupInDays;
        private Set<Book> books = new HashSet<>();
        private int booksScannedPerDay;
        private int booksScore = 0;

        public Library(int signupInDays,
                       int booksScannedPerDay) {
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
                    "signupInDays=" + signupInDays +
                    ", booksScore=" + booksScore +
                    ", booksPerDay=" + booksScannedPerDay +
                    ", score=" + score() +
                    ", booksScannedPerDay=" + booksScannedPerDay +
                    '}';
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
}
