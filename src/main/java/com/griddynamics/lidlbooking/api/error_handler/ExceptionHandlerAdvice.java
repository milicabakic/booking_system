package com.griddynamics.lidlbooking.api.error_handler;

import com.griddynamics.lidlbooking.api.dto.ErrorDTO;
import com.griddynamics.lidlbooking.commons.errors.BadRequestException;
import com.griddynamics.lidlbooking.commons.errors.BaseException;
import com.griddynamics.lidlbooking.commons.errors.ElementNotFoundException;
import com.griddynamics.lidlbooking.commons.errors.SQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.UUID;

@RestControllerAdvice
public class ExceptionHandlerAdvice {

    private static Logger logger = LoggerFactory.getLogger(ExceptionHandlerAdvice.class);

    public static String getTime() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("hh:mm:ss dd-MM-yyyy"));
    }

    private static ErrorDTO createErrorDTO(final BaseException e) {
        return new ErrorDTO(e.getUUID(), e.getMessage(), getTime());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ElementNotFoundException.class)
    public ErrorDTO handleNoElementExceptions(final ElementNotFoundException exception) {
        ErrorDTO errorDTO = createErrorDTO(exception);
        logger.error(errorDTO + " : " + Arrays.toString(exception.getStackTrace()));
        return errorDTO;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadRequestException.class)
    public ErrorDTO handleBadRequestExceptions(final BadRequestException exception) {
        ErrorDTO errorDTO = createErrorDTO(exception);
        logger.error(errorDTO + " : " + Arrays.toString(exception.getStackTrace()));
        return errorDTO;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(SQLException.class)
    public ErrorDTO handleSQLException(final SQLException exception) {
        ErrorDTO errorDTO = createErrorDTO(exception);
        logger.error(errorDTO + " : " + Arrays.toString(exception.getStackTrace()));
        return errorDTO;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorDTO handleMethodArgumentNotValidException(final MethodArgumentNotValidException exception) {
        ErrorDTO errorDTO = new ErrorDTO(UUID.randomUUID(), exception.getMessage(), getTime());
        logger.error(errorDTO + " : " + Arrays.toString(exception.getStackTrace()));
        return errorDTO;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ErrorDTO handleHttpMessageNotReadableException(final HttpMessageNotReadableException exception) {
        ErrorDTO errorDTO = new ErrorDTO(UUID.randomUUID(), exception.getMessage(), getTime());
        logger.error(errorDTO + " : " + Arrays.toString(exception.getStackTrace()));
        return errorDTO;
    }
}
