package com.assignment.libraryapp.controller;

import com.assignment.libraryapp.model.dto.BorrowingDTO;
import com.assignment.libraryapp.service.BorrowingService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Borrowings API")
@RestController
@RequestMapping("/api/v1/borrowings")
public class BorrowingController {

    private final BorrowingService borrowingService;

    public BorrowingController(BorrowingService borrowingService) {
        this.borrowingService = borrowingService;
    }

    @GetMapping
    public List<BorrowingDTO> getAllMappings() {
        return borrowingService.getAllBorrowings();
    }
}