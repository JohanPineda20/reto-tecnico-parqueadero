package com.nelumbo.parking.infraestructure.exceptionhandler;

import com.nelumbo.parking.domain.utils.exceptions.DataAlreadyExistsException;
import com.nelumbo.parking.domain.utils.exceptions.DataNotFoundException;
import com.nelumbo.parking.domain.utils.exceptions.DomainException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

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
                HttpStatus.FORBIDDEN.value()
        );
        STATUS_CODES.put(
                NoResourceFoundException.class.getSimpleName(),
                HttpStatus.NOT_FOUND.value()
        );
        STATUS_CODES.put(
                MissingServletRequestParameterException.class.getSimpleName(),
                HttpStatus.BAD_REQUEST.value()
        );
        STATUS_CODES.put(
                DataNotFoundException.class.getSimpleName(),
                HttpStatus.NOT_FOUND.value()
        );
        STATUS_CODES.put(
                DataAlreadyExistsException.class.getSimpleName(),
                HttpStatus.CONFLICT.value()
        );
        STATUS_CODES.put(
                DomainException.class.getSimpleName(),
                HttpStatus.CONFLICT.value()
        );
    }
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<InfoErrorNotification> handleAllExceptions(Exception exception) {
        ResponseEntity<InfoErrorNotification> result;
        InfoErrorNotification infoErrorNotification = buildErrorNotification(exception);
        log.info(infoErrorNotification.getExceptionName() + ": " + infoErrorNotification.getMessage());
        result = new ResponseEntity<>(infoErrorNotification, HttpStatus.valueOf(infoErrorNotification.getStatusCode()));
        return result;
    }
    private InfoErrorNotification buildErrorNotification(Exception exception) {
        String exceptionName = exception.getClass().getSimpleName();
        String message=null;
        Map<String, String> details=null;
        if (exception instanceof MethodArgumentNotValidException methodArgumentNotValidException) {
            details = extractValidationErrors(methodArgumentNotValidException);
        } else {
            message = exception.getMessage();
        }
        Integer statusCode = STATUS_CODES.get(exceptionName);
        if (statusCode == null) statusCode = HttpStatus.INTERNAL_SERVER_ERROR.value();
        LocalDateTime timestamp = LocalDateTime.now();
        return InfoErrorNotification.builder()
                .exceptionName(exceptionName)
                .details(details)
                .message(message)
                .statusCode(statusCode)
                .timestamp(timestamp)
                .build();
    }
    private Map<String, String> extractValidationErrors(MethodArgumentNotValidException exception) {
        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult().getFieldErrors().forEach(field -> errors.put(field.getField(), field.getDefaultMessage()));
        return errors;
    }
}
