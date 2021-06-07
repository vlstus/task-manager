package com.study.taskmanagement.controller.exception;

import com.study.taskmanagement.service.exception.BusinessLayerException;
import com.study.taskmanagement.service.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
public class RestExceptionHandler {

    private final MessageSourceAccessor messageSourceAccessor;
    private final ConstraintViolationMessageCodesProvider constraintViolationMessageCodesProvider;


    @ExceptionHandler({
            ConstraintViolationException.class
    })
    public ResponseEntity<ErrorInfo> handleDuplicate(ConstraintViolationException constraintViolationException) {
        String constraintMessage = messageSourceAccessor.getMessage(
                constraintViolationMessageCodesProvider.getCode(constraintViolationException));
        HttpStatus conflictStatus = HttpStatus.CONFLICT;
        return ResponseEntity.status(conflictStatus)
                .body(new ErrorInfo(conflictStatus, constraintMessage));
    }

    @ExceptionHandler({
            MethodArgumentNotValidException.class
    })
    public ResponseEntity<ErrorInfo> handleValidationError(MethodArgumentNotValidException validationException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorInfo(HttpStatus.BAD_REQUEST, validationException.getAllErrors().stream()
                        .map(messageSourceAccessor::getMessage)
                        .toArray(String[]::new)));
    }

    @ExceptionHandler({
            BusinessLayerException.class
    })
    public ResponseEntity<ErrorInfo> handleBusinessException(NotFoundException notFoundException) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorInfo(HttpStatus.NOT_FOUND, messageSourceAccessor.getMessage(notFoundException)));
    }

    @ExceptionHandler({
            BadCredentialsException.class
    })
    public ResponseEntity<ErrorInfo> handleAuthError(BadCredentialsException badCredentialsException) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ErrorInfo(HttpStatus.UNAUTHORIZED,
                        messageSourceAccessor.getMessage("application.login.error")));
    }
}
