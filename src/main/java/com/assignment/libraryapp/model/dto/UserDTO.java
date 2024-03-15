package com.assignment.libraryapp.model.dto;

import java.time.LocalDate;

public record UserDTO(long id, String firstName, String lastName, LocalDate memberSince, LocalDate memberTill, String gender) {
}