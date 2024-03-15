package com.assignment.libraryapp.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import com.assignment.libraryapp.model.Book;

@DataJpaTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @BeforeEach
    public void setUp() {
        bookRepository.deleteAll();
    }

    @Test
    public void testFindByIsBorrowed() {
        List<Book> books = generateBooks();
        bookRepository.saveAll(books);

        List<Book> borrowedBooks = bookRepository.findByIsBorrowed(true);
        List<Book> availableBooks = bookRepository.findByIsBorrowed(false);

        assertEquals(1, borrowedBooks.size());
        assertEquals(1, availableBooks.size());
        assertEquals("Book 1", availableBooks.get(0).getTitle());
        assertEquals("Book 2", borrowedBooks.get(0).getTitle());
    }

    @Test
    public void testFindByTitle() {
        List<Book> books = generateBooks();
        bookRepository.saveAll(books);

        List<Book> foundBooks = bookRepository.findByTitle("Book 1");

        assertEquals(1, foundBooks.size());
        assertEquals("Book 1", foundBooks.get(0).getTitle());
    }

    private List<Book> generateBooks() {
        List<Book> books = new ArrayList<>();
        books.add(new Book("Book 1", "Author 1", "Genre 1", "Publisher 1", false));
        books.add(new Book("Book 2", "Author 2", "Genre 2", "Publisher 2", true));
        return books;
    }
}
