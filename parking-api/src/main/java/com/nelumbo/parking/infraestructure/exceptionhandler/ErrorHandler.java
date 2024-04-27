package com.nelumbo.parking.infraestructure.exceptionhandler;

import com.nelumbo.parking.domain.utils.exceptions.DataAlreadyExistsException;
import com.nelumbo.parking.domain.utils.exceptions.DataNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
@Slf4j
@ControllerAdvice
public class ErrorHandler {
    private static final ConcurrentMap<String, Integer> STATUS_CODES = new ConcurrentHashMap<>();
    public ErrorHandler() {
        STATUS_CODES.put(
                MethodArgumentNotValidException.class.getSimpleName(),
                HttpStatus.BAD_REQUEST.value()
        );
        STATUS_CODES.put(
                MethodArgumentTypeMismatchException.class.getSimpleName(),
                HttpStatus.BAD_REQUEST.value()
        );
        STATUS_CODES.put(
                AccessDeniedException.class.getSimpleName(),
                HttpStatus.UNAUTHORIZED.value()
        );
        STATUS_CODES.put(
                DataNotFoundException.class.getSimpleName(),
                HttpStatus.NOT_FOUND.value()
        );
        STATUS_CODES.put(
                DataAlreadyExistsException.class.getSimpleName(),
                HttpStatus.CONFLICT.value()
        );
    }
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<InfoErrorNotification> handleAllExceptions(Exception exception, HandlerMethod handlerMethod) {
        ResponseEntity<InfoErrorNotification> result;
        InfoErrorNotification infoErrorNotification = buildErrorNotification(exception, handlerMethod);
        log.info(infoErrorNotification.getExceptionName() + ": " + infoErrorNotification.getMessage());
        result = new ResponseEntity<>(infoErrorNotification, HttpStatus.valueOf(infoErrorNotification.getStatusCode()));
        return result;
    }
    private InfoErrorNotification buildErrorNotification(Exception exception, HandlerMethod handlerMethod) {
        String exceptionName = exception.getClass().getSimpleName();
        String message = exception.getMessage();
        Integer statusCode = STATUS_CODES.get(exceptionName);
        if (statusCode == null) statusCode = HttpStatus.INTERNAL_SERVER_ERROR.value();
        LocalDateTime timestamp = LocalDateTime.now();
        String controllerName = handlerMethod.getMethod().getDeclaringClass().toString();
        return InfoErrorNotification.builder()
                .exceptionName(exceptionName)
                .message(message)
                .statusCode(statusCode)
                .timestamp(timestamp)
                .controllerError(controllerName)
                .build();
    }
}
