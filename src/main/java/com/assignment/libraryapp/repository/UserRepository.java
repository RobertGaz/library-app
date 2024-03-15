package com.assignment.libraryapp.repository;

import com.assignment.libraryapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    String SQL = """
            select u from User u
            where (u.memberTill is null or u.memberTill >= current_date)
            and id not in (select distinct b.user.id from Borrowing b
                           where b.startDate <= current_date and b.endDate >= current_date)
            """;

    List<User> findByBorrowingsIsNotNull();

    @Query(value = SQL)
    List<User> findActiveUsersWithoutCurrentBorrowings();

    List<User> findByBorrowingsStartDate(LocalDate date);

    List<User> findByFirstNameAndLastName(String firstName, String lastName);
}