package com.assignment.libraryapp.dataset;

import com.assignment.libraryapp.model.Book;
import com.assignment.libraryapp.model.Borrowing;
import com.assignment.libraryapp.model.User;
import com.assignment.libraryapp.repository.BookRepository;
import com.assignment.libraryapp.repository.BorrowingRepository;
import com.assignment.libraryapp.repository.UserRepository;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * This class loads the dataset to DB on application startup. Not for production use.
 */
@Deprecated
@Component
public class DatasetLoader implements ApplicationRunner {
    private static final Logger LOG = LoggerFactory.getLogger(DatasetLoader.class);

    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final BorrowingRepository borrowingRepository;
    private final CSVFormat csvFormat;

    public DatasetLoader(BookRepository bookRepository,
                         UserRepository userRepository,
                         BorrowingRepository borrowingRepository) {
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
        this.borrowingRepository = borrowingRepository;
        this.csvFormat = CSVFormat.DEFAULT.builder()
                .setSkipHeaderRecord(true)
                .setHeader()
                .build();    }

    @Value("classpath:data/books.csv")
    private Resource booksResource;

    @Value("classpath:data/user.csv")
    private Resource usersResource;

    @Value("classpath:data/borrowed.csv")
    private Resource borrowingsResource;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        LOG.info("loading the books...");
        loadBooks();
        LOG.info("successfully loaded the books");

        LOG.info("loading the users...");
        loadUsers();
        LOG.info("successfully loaded the users");

        LOG.info("loading the borrowings...");
        loadBorrowings();
        LOG.info("successfully loaded the borrowings");
    }

    private void loadBooks() {
        for (CSVRecord record : getRecords(booksResource)) {
            String title = record.get(0);
            String author = record.get(1);
            String genre = record.get(2);
            String publisher = record.get(3);
            boolean isBorrowed = false; // for saving time I assume that all borrowings from csv are old enough

            Book book = new Book(title, author, genre, publisher, isBorrowed);

            if (StringUtils.hasText(title)) {
                System.out.println(book);
                bookRepository.save(book);
            }

        }
    }

    private void loadUsers() {
        for (CSVRecord record : getRecords(usersResource)) {
            String lastName = record.get(0);
            String firstName = record.get(1);
            String memberSince = record.get(2);
            String memberTill = record.get(3);
            String gender = record.get(4);

            if (StringUtils.hasText(lastName) && StringUtils.hasText(firstName)) {
                User user = new User(firstName, lastName, readDate(memberSince), readDate(memberTill), gender);
                System.out.println(user);
                userRepository.save(user);
            }

        }
    }

    private void loadBorrowings() {
        for (CSVRecord record : getRecords(borrowingsResource)) {
            String borrowerName = record.get(0);
            String bookName = record.get(1);
            String borrowedFrom = record.get(2);
            String borrowedTo = record.get(3);

            if (StringUtils.hasText(borrowerName) && StringUtils.hasText(bookName)) {

                Book book = bookRepository.findByTitle(bookName).get(0);
                String lastName = borrowerName.split(",")[0];
                String firstName = borrowerName.split(",")[1];
                User borrower = userRepository.findByFirstNameAndLastName(firstName, lastName).get(0);

                Borrowing borrowing = new Borrowing(book, borrower, readDate(borrowedFrom), readDate(borrowedTo));

                System.out.println(borrowing);
                borrowingRepository.save(borrowing);
            }

        }
    }

    private LocalDate readDate(String date) {
        if (StringUtils.hasText(date)) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            return LocalDate.parse(date, formatter);
        }

        return null;
    }

    private Iterable<CSVRecord> getRecords(Resource resource) {
        try {
            return csvFormat.parse(new InputStreamReader(resource.getInputStream()));
        } catch (IOException e) {
            LOG.error("Error happened during loading the dataset, killing the application...");
            throw new RuntimeException(e);
        }
    }
}