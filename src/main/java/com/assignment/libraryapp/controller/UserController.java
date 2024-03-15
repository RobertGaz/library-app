package com.assignment.libraryapp.controller;

import com.assignment.libraryapp.model.dto.UserDTO;
import com.assignment.libraryapp.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Tag(name = "Users API")
@RestController
@RequestMapping("api/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    // a) returns all users who have actually borrowed at least one book
    // and
    // c) returns all users who have borrowed a book on a given date
    @GetMapping("/with-borrowings")
    public List<UserDTO> getUsersWithBorrowings(@RequestParam(value = "on_date", required = false)
                                             @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return date == null ? userService.getUsersWithBorrowings()
                            : userService.getUsersWhoBorrowedOnDate(date);
    }

    // b) returns all non-terminated users who have not currently borrowed anything
    @GetMapping("/non-terminated-without-borrowings")
    public List<UserDTO> getNonTerminatedUsersWithNoBorrowings() {
        return userService.getNonTerminatedUsersWithNoBorrowings();
    }
}