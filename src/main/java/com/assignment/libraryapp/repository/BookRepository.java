package com.assignment.libraryapp.repository;

import com.assignment.libraryapp.model.Book;
import com.assignment.libraryapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByIsBorrowed(boolean isBorrowed);

    List<Book> findByTitle(String title);

    List<Book> findByBorrowingsUserAndBorrowingsStartDateBetween(User user, LocalDate startDate, LocalDate endDate);

}