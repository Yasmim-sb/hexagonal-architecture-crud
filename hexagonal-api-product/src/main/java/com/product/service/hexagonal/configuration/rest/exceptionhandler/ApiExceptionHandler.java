package com.product.service.hexagonal.configuration.rest.exceptionhandler;

import com.product.service.hexagonal.configuration.rest.error.BaseErrorDTO;
import com.product.service.hexagonal.configuration.rest.error.ExceptionErrorDetailsDTO;
import com.product.service.hexagonal.domain.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@RequiredArgsConstructor
public class ApiExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ExceptionErrorDetailsDTO<BaseErrorDTO>> resourceNotFoundException(NotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ExceptionErrorDetailsDTO.<BaseErrorDTO>builder()
                .status(HttpStatus.NOT_FOUND.name())
                .message(ex.getMessage())
                .build());
    }
}
