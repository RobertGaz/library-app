package com.assignment.libraryapp.model.dto;

import java.time.LocalDate;

public record BorrowingDTO(long id, long bookId, long userId, LocalDate startDate, LocalDate endDate) {
}