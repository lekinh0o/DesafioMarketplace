package com.frwk.marketplace.core.exceptions.handler;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.frwk.marketplace.core.exceptions.EntityNotFoundException;
import com.frwk.marketplace.core.exceptions.GeneralException;
import com.frwk.marketplace.core.exceptions.InvalidCartException;
import com.frwk.marketplace.core.exceptions.InvalidClientException;
import com.frwk.marketplace.core.exceptions.InvalidFormatException;
import com.frwk.marketplace.core.exceptions.InvalidProductException;
import com.frwk.marketplace.core.response.ErrorObject;
import com.frwk.marketplace.core.response.ResponseError;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class RestExceptionHandler  {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<ErrorObject> errors = ex.getBindingResult().getAllErrors().stream()
                .map(err -> this.parseFieldErrorToBaseError((FieldError) err))
                .collect(Collectors.toList());
    
        Object response = ResponseError.builder().type(
                        "ModelValidationException")
                        .message("Dados enviados inválidos").erros(errors).build();
        return new ResponseEntity<>(response,HttpStatus.PRECONDITION_FAILED);
    }

    @ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity<Object> handleValidationExceptions(InvalidFormatException ex) {
       
          Object response = ResponseError.builder().type(ex.getCause().toString()).message("ModelValidationException")
                        .erros(Collections.singletonList(ErrorObject.builder().field(ex.getField()).error(ex.getMessage()).build()));
        return new ResponseEntity<>(response, HttpStatus.PRECONDITION_FAILED);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> handleValidationExceptions(EntityNotFoundException ex) {
        return this.handleBaseException(ex);
    }

    @ExceptionHandler(InvalidProductException.class)
    public ResponseEntity<Object> handleValidationExceptions(InvalidProductException ex) {
        return this.handleBaseException(ex);
    }

    @ExceptionHandler(InvalidClientException.class)
    public ResponseEntity<Object> handleValidationExceptions(InvalidClientException ex) {
        return this.handleBaseException(ex);
    }

    @ExceptionHandler(InvalidCartException.class)
    public ResponseEntity<Object> handleValidationExceptions(InvalidCartException ex) {
        return this.handleBaseException(ex);
    }

    private ResponseEntity<Object> handleBaseException(GeneralException ex) {
        
          Object response = ResponseError.builder().type(ex.getType()).message(ex.getMessage()).build();
        return new ResponseEntity<>(response,HttpStatus.PRECONDITION_FAILED);
    }

    private ErrorObject parseFieldErrorToBaseError(FieldError error) {
        return ErrorObject.builder().field(error.getField()).error(error.getDefaultMessage()).build();
    }
  

}
