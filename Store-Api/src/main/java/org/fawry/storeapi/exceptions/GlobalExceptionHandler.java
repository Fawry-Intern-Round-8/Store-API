package org.fawry.storeapi.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.fawry.storeapi.dtos.error.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponseDTO> handleRuntimeException(RuntimeException ex, HttpServletRequest request) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(
                Instant.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Bad Request",
                ex.getMessage(),
                request.getRequestURI()
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
