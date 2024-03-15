package com.assignment.libraryapp.controller;

import com.assignment.libraryapp.model.dto.BookDTO;
import com.assignment.libraryapp.model.dto.UserDTO;
import com.assignment.libraryapp.exception.NotFoundException;
import com.assignment.libraryapp.service.BookService;
import com.assignment.libraryapp.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@Tag(name = "Books API")
@RestController
@RequestMapping("/api/v1/books")
public class BookController {

    private final BookService bookService;

    private final UserService userService;

    public BookController(BookService bookService, UserService userService) {
        this.bookService = bookService;
        this.userService = userService;
    }

    // e) returns all available (not borrowed) books
    @GetMapping
    public List<BookDTO> getBooks(@RequestParam(value = "available_only", required = false) boolean availableOnly) {
        return availableOnly ? bookService.getAvailableBooks()
                             : bookService.getAllBooks();
    }

    // d) returns all books borrowed by a given user in a given date range
    @GetMapping("/borrowed-by-user-in-date-range")
    public List<BookDTO> getBooksBorrowedByUserInDateRange(@RequestParam("user_id") Long userId,
                                                           @RequestParam("from_date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                                           @RequestParam("to_date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        UserDTO user = userService.getUserById(userId).orElseThrow(() -> new NotFoundException("User not found with id: " + userId));
        return bookService.getBooksBorrowedByUserInDateRange(user, startDate, endDate);
    }
}
