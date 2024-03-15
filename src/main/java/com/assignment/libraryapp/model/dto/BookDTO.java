package com.assignment.libraryapp.model.dto;

public record BookDTO(long id, String title, String author, String genre, String publisher, boolean isBorrowed) {
}