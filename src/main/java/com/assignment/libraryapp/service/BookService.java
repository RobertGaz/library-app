package com.assignment.libraryapp.service;

import com.assignment.libraryapp.model.dto.BookDTO;
import com.assignment.libraryapp.model.dto.UserDTO;
import com.assignment.libraryapp.model.mapper.LibraryMapper;
import com.assignment.libraryapp.model.Book;
import com.assignment.libraryapp.model.User;
import com.assignment.libraryapp.repository.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class BookService {
    private static final Logger LOG = LoggerFactory.getLogger(BookService.class);

    private final BookRepository bookRepository;

    private final LibraryMapper mapper;

    public BookService(BookRepository bookRepository, LibraryMapper mapper) {
        this.bookRepository = bookRepository;
        this.mapper = mapper;
    }

    public List<BookDTO> getAllBooks() {
        LOG.info("getting all books...");
        List<Book> books = bookRepository.findAll();
        return mapper.toBookDTOs(books);
    }

    public List<BookDTO> getBooksBorrowedByUserInDateRange(UserDTO userDTO, LocalDate startDate, LocalDate endDate) {
        LOG.info("getting all books borrowed by user {} within a date range betweend {} and {}...", userDTO.id(), startDate, endDate);
        User user = mapper.toUser(userDTO);
        List<Book> books = bookRepository.findByBorrowingsUserAndBorrowingsStartDateBetween(user, startDate, endDate);
        return mapper.toBookDTOs(books);
    }

    public List<BookDTO> getAvailableBooks() {
        LOG.info("getting available books...");
        List<Book> books = bookRepository.findByIsBorrowed(false);
        return mapper.toBookDTOs(books);
    }
}