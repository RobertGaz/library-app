package com.assignment.libraryapp.exception;

import com.assignment.libraryapp.model.dto.ErrorDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.UUID;

@ControllerAdvice
public class ErrorHandler {

    private static final Logger LOG = LoggerFactory.getLogger(ErrorHandler.class);

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorDTO> handleUserNotFoundException(NotFoundException e) {
        UUID errorId = UUID.randomUUID();
        LOG.error("NotFoundException occurred: {}. Assigned UUID: {}", e.getMessage(), errorId, e);

        ErrorDTO error = new ErrorDTO(errorId, "client", e.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErrorDTO> handleNoResourceFoundException(NoResourceFoundException e) {
        UUID errorId = UUID.randomUUID();
        LOG.error("NoResourceFoundException occurred: {}. Assigned UUID: {}", e.getMessage(), errorId, e);

        ErrorDTO error = new ErrorDTO(errorId, "client", e.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ErrorDTO> handleGenericException(Throwable e) {
        UUID errorId = UUID.randomUUID();
        LOG.error("Exception occurred: {}. Assigned UUID: {}", e.getMessage(), errorId, e);

        ErrorDTO error = new ErrorDTO(errorId, "server", e.getMessage());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}