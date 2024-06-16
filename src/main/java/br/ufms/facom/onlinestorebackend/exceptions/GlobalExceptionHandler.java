package br.ufms.facom.onlinestorebackend.exceptions;

import io.jsonwebtoken.ExpiredJwtException;
import org.hibernate.ObjectNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;
import java.security.SignatureException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleSecurityException(Exception exception) {
        Map<String, Object> errorDetail = new HashMap<>();

        // Print stack trace for debugging
        exception.printStackTrace();

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        String description = "Unknown internal server error.";

        if (exception instanceof BadCredentialsException) {
            status = HttpStatus.UNAUTHORIZED;
            description = "The username or password is incorrect";
        } else if (exception instanceof AccountStatusException) {
            status = HttpStatus.FORBIDDEN;
            description = "The account is locked";
        } else if (exception instanceof AccessDeniedException) {
            status = HttpStatus.FORBIDDEN;
            description = "You are not authorized to access this resource";
        } else if (exception instanceof SignatureException) {
            status = HttpStatus.FORBIDDEN;
            description = "The JWT signature is invalid";
        } else if (exception instanceof ExpiredJwtException) {
            status = HttpStatus.FORBIDDEN;
            description = "The JWT token has expired";
        } else if (exception instanceof HttpMessageNotReadableException) {
            status = HttpStatus.BAD_REQUEST;
            description = "The request body is invalid";
        } else if (exception instanceof IllegalArgumentException || exception instanceof IllegalStateException) {
            status = HttpStatus.BAD_REQUEST;
            description = "The request is invalid";
        } else if (exception instanceof ObjectNotFoundException) {
            status = HttpStatus.NOT_FOUND;
            description = "The requested resource was not found";
        } else if (exception instanceof AuthenticationException) {
            status = HttpStatus.UNAUTHORIZED;
            description = "You are not authenticated";
        }

        errorDetail.put("status", status.value());
        errorDetail.put("error", status.getReasonPhrase());
        errorDetail.put("message", exception.getMessage());
        errorDetail.put("description", description);

        return ResponseEntity.status(status).body(errorDetail);
    }
}
