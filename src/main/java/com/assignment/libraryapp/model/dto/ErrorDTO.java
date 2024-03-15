package com.assignment.libraryapp.model.dto;

import java.util.UUID;

public record ErrorDTO(UUID id, String source, String detail) {
}