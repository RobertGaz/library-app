package com.assignment.libraryapp.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String firstName;
    private String lastName;
    private LocalDate memberSince;
    private LocalDate memberTill;
    private String gender;

    @OneToMany(mappedBy = "user")
    private List<Borrowing> borrowings;

    public User() {
    }

    public User(String firstName, String lastName, LocalDate memberSince, LocalDate memberTill, String gender) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.memberSince = memberSince;
        this.memberTill = memberTill;
        this.gender = gender;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getMemberSince() {
        return memberSince;
    }

    public void setMemberSince(LocalDate memberSince) {
        this.memberSince = memberSince;
    }

    public LocalDate getMemberTill() {
        return memberTill;
    }

    public void setMemberTill(LocalDate memberTill) {
        this.memberTill = memberTill;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public List<Borrowing> getBorrowings() {
        return borrowings;
    }

    public void setBorrowings(List<Borrowing> borrowings) {
        this.borrowings = borrowings;
    }

    @Override
    public String toString() {
        return "User{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", memberSince=" + memberSince +
                ", memberTill=" + memberTill +
                ", gender=" + gender +
                '}';
    }
}