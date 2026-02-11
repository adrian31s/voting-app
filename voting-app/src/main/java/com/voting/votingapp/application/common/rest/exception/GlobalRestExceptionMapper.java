package com.voting.votingapp.application.common.rest.exception;

import com.voting.votingapp.application.common.rest.exception.model.RestErrorDto;
import com.voting.votingapp.application.common.service.exception.ServiceException;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalRestExceptionMapper {
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String, String>> handleValidation(MethodArgumentNotValidException ex) {
    Map<String, String> errors = new HashMap<>();
    ex.getBindingResult()
        .getFieldErrors()
        .forEach(err -> errors.put(err.getField(), err.getDefaultMessage()));

    return ResponseEntity.badRequest().body(errors);
  }

  @ExceptionHandler(ServiceException.class)
  public ResponseEntity<RestErrorDto> handleServiceException(ServiceException ex) {
    return ResponseEntity.status(HttpStatus.CONFLICT)
        .body(new RestErrorDto("Operation failed", ex.getMessage()));
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<RestErrorDto> handleGenericException(Exception ex) {
    log.error("Unexpected exception occurred", ex);
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(new RestErrorDto("Internal server error", "An unexpected error occurred"));
  }
}
