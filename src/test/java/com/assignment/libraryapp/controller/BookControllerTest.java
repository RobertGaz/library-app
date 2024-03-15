package com.assignment.libraryapp.controller;

import com.assignment.libraryapp.model.dto.BookDTO;
import com.assignment.libraryapp.model.dto.UserDTO;
import com.assignment.libraryapp.service.BookService;
import com.assignment.libraryapp.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;


@WebMvcTest(BookController.class)
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @MockBean
    private UserService userService;

    @Test
    public void testGetBooksBorrowedByUserInDateRange() throws Exception {
        LocalDate startDate = LocalDate.of(2023, 1, 1);
        LocalDate endDate = LocalDate.of(2023, 12, 31);

        UserDTO userDTO = new UserDTO(1, "Name", "Surname", LocalDate.MIN, LocalDate.MAX, "m");

        when(userService.getUserById(anyLong())).thenReturn(Optional.of(userDTO));
        when(bookService.getBooksBorrowedByUserInDateRange(eq(userDTO), eq(startDate), eq(endDate)))
                .thenReturn(generateBookDTOs());

        mockMvc.perform(get("/api/v1/books/borrowed-by-user-in-date-range")
                        .param("user_id", "1")
                        .param("from_date", "2023-01-01")
                        .param("to_date", "2023-12-31"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void testGetAllBooks() throws Exception {
        when(bookService.getAllBooks())
                .thenReturn(generateBookDTOs());

        mockMvc.perform(get("/api/v1/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void testGetAvailableBooks() throws Exception {
        when(bookService.getAvailableBooks())
                .thenReturn(generateBookDTOs());

        mockMvc.perform(get("/api/v1/books")
                        .param("available_only", "true"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    private List<BookDTO> generateBookDTOs() {
        List<BookDTO> bookDTOs = new ArrayList<>();
        bookDTOs.add(new BookDTO(1, "Book 1", "Author 1", "Genre 1", "Publisher 1", false));
        bookDTOs.add(new BookDTO(2, "Book 2", "Author 2", "Genre 2", "Publisher 2", true));
        return bookDTOs;
    }
}