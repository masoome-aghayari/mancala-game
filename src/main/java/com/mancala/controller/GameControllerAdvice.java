package com.mancala.controller;

/*
 * @author masoome.aghayari
 * @since 12/29/23
 */

import com.mancala.model.dto.ErrorResponse;
import com.mancala.model.exceptions.EmptyPocketException;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.method.MethodValidationException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.MethodNotAllowedException;

import java.util.Date;

@RestControllerAdvice
@Slf4j
public class GameControllerAdvice {

    private static String getRequestURI(WebRequest request) {
        return ((ServletWebRequest) request).getRequest().getRequestURI();
    }

    @ExceptionHandler({MethodValidationException.class, TypeMismatchException.class, EmptyPocketException.class,
            MissingPathVariableException.class, MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<ErrorResponse> handleBadRequestException(Exception ex, WebRequest request) {
        logError(ex.getMessage(), getRequestURI(request));
        return getErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST, getRequestURI(request));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorResponse> handleEntityNotFoundException(Exception ex, WebRequest request) {
        logError(ex.getMessage(), getRequestURI(request));
        return getErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND, getRequestURI(request));
    }

    @ExceptionHandler({MethodNotAllowedException.class, HttpRequestMethodNotSupportedException.class})
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public ResponseEntity<ErrorResponse> handleMethodNotAllowedException(Exception ex, WebRequest request) {
        logError(ex.getMessage(), getRequestURI(request));
        return getErrorResponse(ex.getMessage(), HttpStatus.METHOD_NOT_ALLOWED, getRequestURI(request));
    }

    @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    protected ResponseEntity<ErrorResponse> handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException ex,
                                                                             WebRequest request) {
        logError(ex.getMessage(), getRequestURI(request));
        return getErrorResponse(ex.getMessage(), HttpStatus.NOT_ACCEPTABLE, getRequestURI(request));
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    public ResponseEntity<ErrorResponse> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex,
                                                                         WebRequest request) {
        logError(ex.getMessage(), getRequestURI(request));
        return getErrorResponse(ex.getMessage(), HttpStatus.UNSUPPORTED_MEDIA_TYPE, getRequestURI(request));
    }

    @ExceptionHandler({RuntimeException.class, Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorResponse> handleGeneralExceptions(Exception ex, WebRequest request) {
        logError(ex.getMessage(), getRequestURI(request));
        return getErrorResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, getRequestURI(request));
    }

    private ResponseEntity<ErrorResponse> getErrorResponse(String message, HttpStatus status, String path) {
        var errorResponse = new ErrorResponse(message, status.value(), new Date(), path);
        return new ResponseEntity<>(errorResponse, status);
    }

    private void logError(String message, String path) {
        log.error("exception occurred with message: {}, on path: {}, ", message, path);
    }
}
