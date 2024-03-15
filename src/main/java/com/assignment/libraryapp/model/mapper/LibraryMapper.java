package com.assignment.libraryapp.model.mapper;

import com.assignment.libraryapp.model.dto.BookDTO;
import com.assignment.libraryapp.model.dto.BorrowingDTO;
import com.assignment.libraryapp.model.dto.UserDTO;
import com.assignment.libraryapp.model.Book;
import com.assignment.libraryapp.model.Borrowing;
import com.assignment.libraryapp.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LibraryMapper {

    List<BookDTO> toBookDTOs(List<Book> books);

    User toUser(UserDTO userDTO);

    UserDTO toUserDTO(User user);
    List<UserDTO> toUserDTOs(List<User> users);

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "bookId", source = "book.id")
    BorrowingDTO toBorrowingDTO(Borrowing borrowing);
    List<BorrowingDTO> toBorrowingDTOs(List<Borrowing> borrowings);
}