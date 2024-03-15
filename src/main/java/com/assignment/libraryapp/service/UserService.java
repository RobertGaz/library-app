package com.assignment.libraryapp.service;

import com.assignment.libraryapp.model.dto.UserDTO;
import com.assignment.libraryapp.model.mapper.LibraryMapper;
import com.assignment.libraryapp.model.User;
import com.assignment.libraryapp.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private static final Logger LOG = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;


    private final LibraryMapper mapper;

    public UserService(UserRepository userRepository, LibraryMapper mapper) {
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    public List<UserDTO> getAllUsers() {
        LOG.info("getting all users...");
        List<User> users = userRepository.findAll();
        return mapper.toUserDTOs(users);
    }

    public List<UserDTO> getUsersWithBorrowings() {
        LOG.info("getting all users with borrowings...");
        List<User> users = userRepository.findByBorrowingsIsNotNull();
        return mapper.toUserDTOs(users);
    }

    public List<UserDTO> getNonTerminatedUsersWithNoBorrowings() {
        LOG.info("getting all non-terminated users with borrowings...");
        List<User> users = userRepository.findActiveUsersWithoutCurrentBorrowings();
        return mapper.toUserDTOs(users);
    }

    public List<UserDTO> getUsersWhoBorrowedOnDate(LocalDate date) {
        LOG.info("searching for users borrowed on date {}...", date);
        List<User> users = userRepository.findByBorrowingsStartDate(date);
        return mapper.toUserDTOs(users);
    }

    public Optional<UserDTO> getUserById(Long id) {
        LOG.info("getting user by id {}...", id);
        Optional<User> user = userRepository.findById(id);
        return user.map(mapper::toUserDTO);
    }
}