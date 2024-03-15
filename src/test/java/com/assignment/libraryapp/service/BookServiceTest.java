package com.assignment.libraryapp.service;

import com.assignment.libraryapp.model.Book;
import com.assignment.libraryapp.model.dto.BookDTO;
import com.assignment.libraryapp.model.dto.UserDTO;
import com.assignment.libraryapp.model.mapper.LibraryMapper;
import com.assignment.libraryapp.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private LibraryMapper mapper;

    private BookService bookService;

    @BeforeEach
    public void setUp() {
        bookService = new BookService(bookRepository, mapper);
    }

    @Test
    public void testGetAllBooks() {
        List<Book> books = generateBooks();
        when(bookRepository.findAll()).thenReturn(books);

        List<BookDTO> expectedBookDTOs = generateBookDTOs();
        when(mapper.toBookDTOs(books)).thenReturn(expectedBookDTOs);

        List<BookDTO> actualBookDTOs = bookService.getAllBooks();

        assertEquals(expectedBookDTOs, actualBookDTOs);
        verify(bookRepository, times(1)).findAll();
        verify(mapper, times(1)).toBookDTOs(books);
    }

    @Test
    public void testGetAvailableBooks() {
        List<Book> books = generateBooks();
        when(bookRepository.findByIsBorrowed(false)).thenReturn(books);

        List<BookDTO> expectedBookDTOs = generateBookDTOs();
        when(mapper.toBookDTOs(books)).thenReturn(expectedBookDTOs);

        List<BookDTO> actualBookDTOs = bookService.getAvailableBooks();

        assertEquals(expectedBookDTOs, actualBookDTOs);
        verify(bookRepository, times(1)).findByIsBorrowed(false);
        verify(mapper, times(1)).toBookDTOs(books);
    }

    @Test
    public void testGetBooksBorrowedByUserInDateRange() {
        UserDTO userDTO = new UserDTO(1, "Name", "Surname", LocalDate.MIN, LocalDate.MAX, "m");
        LocalDate startDate = LocalDate.now().minusDays(7);
        LocalDate endDate = LocalDate.now();

        List<Book> books = new ArrayList<>();
        when(bookRepository.findByBorrowingsUserAndBorrowingsStartDateBetween(any(), eq(startDate), eq(endDate))).thenReturn(books);

        List<BookDTO> expectedBookDTOs = new ArrayList<>();
        when(mapper.toBookDTOs(books)).thenReturn(expectedBookDTOs);

        List<BookDTO> actualBookDTOs = bookService.getBooksBorrowedByUserInDateRange(userDTO, startDate, endDate);

        assertEquals(expectedBookDTOs, actualBookDTOs);
        verify(bookRepository, times(1)).findByBorrowingsUserAndBorrowingsStartDateBetween(any(), eq(startDate), eq(endDate));
        verify(mapper, times(1)).toBookDTOs(books);
    }

    private List<Book> generateBooks() {
        List<Book> books = new ArrayList<>();
        books.add(new Book("Book 1", "Author 1", "Genre 1", "Publisher 1", false));
        books.add(new Book("Book 2", "Author 2", "Genre 2", "Publisher 2", true));
        return books;
    }

    private List<BookDTO> generateBookDTOs() {
        List<BookDTO> bookDTOs = new ArrayList<>();
        bookDTOs.add(new BookDTO(1, "Book 1", "Author 1", "Genre 1", "Publisher 1", false));
        bookDTOs.add(new BookDTO(2, "Book 2", "Author 2", "Genre 2", "Publisher 2", true));
        return bookDTOs;
    }
}