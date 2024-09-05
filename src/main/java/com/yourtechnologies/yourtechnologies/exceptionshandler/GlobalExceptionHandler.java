package com.yourtechnologies.yourtechnologies.exceptionshandler;

import com.yourtechnologies.yourtechnologies.dto.response.BaseResponseDTO;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseResponseDTO> handleException(Exception ex) {
        log.error(ex.getMessage());
        BaseResponseDTO baseResponseDTO = new BaseResponseDTO("");
        HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;
        if (ex instanceof ExpiredJwtException || ex instanceof BadCredentialsException) {
            baseResponseDTO.setMessage("Unauthenticated");
        } else {
            ex.printStackTrace();
            baseResponseDTO.setMessage("internal server error");
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return ResponseEntity.status(httpStatus).body(baseResponseDTO);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}
