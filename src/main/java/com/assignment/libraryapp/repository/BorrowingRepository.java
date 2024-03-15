package com.assignment.libraryapp.repository;

import com.assignment.libraryapp.model.Borrowing;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BorrowingRepository extends JpaRepository<Borrowing, Long> {
}