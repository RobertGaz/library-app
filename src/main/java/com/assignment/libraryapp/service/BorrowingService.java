package com.assignment.libraryapp.service;

import com.assignment.libraryapp.model.dto.BorrowingDTO;
import com.assignment.libraryapp.model.mapper.LibraryMapper;
import com.assignment.libraryapp.model.Borrowing;
import com.assignment.libraryapp.repository.BorrowingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BorrowingService {
    private static final Logger LOG = LoggerFactory.getLogger(BorrowingService.class);

    private final BorrowingRepository borrowingRepository;
    private final LibraryMapper mapper;

    public BorrowingService(BorrowingRepository borrowingRepository, LibraryMapper mapper) {
        this.borrowingRepository = borrowingRepository;
        this.mapper = mapper;
    }

    public List<BorrowingDTO> getAllBorrowings() {
        LOG.info("getting all borrowings...");
        List<Borrowing> borrowings = borrowingRepository.findAll();
        return mapper.toBorrowingDTOs(borrowings);
    }
}