package com.vngrs.clinic.handler;

import com.vngrs.clinic.exception.badrequest.BadRequestErrorDto;
import com.vngrs.clinic.exception.badrequest.BadRequestException;
import com.vngrs.clinic.exception.badrequest.MessageType;
import com.vngrs.clinic.exception.internalserver.InternalServerErrorDto;
import com.vngrs.clinic.exception.internalserver.InternalServerException;
import com.vngrs.clinic.exception.notfound.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.*;
import java.util.stream.Collectors;

@ControllerAdvice
@RestController
@EnableWebMvc
@Slf4j
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    private final MessageSource messageSource;
    private static final String ENTITY_NAME = "clinic";

    @Autowired
    public ControllerExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public BadRequestException processBadRequestException(BadRequestException exception) {
        log.error("BadRequestException: {}", exception.getMessage());
        return exception;
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public NotFoundException processBadRequestException(NotFoundException exception) {
        log.error("BadRequestException: {}", exception.getMessage());
        return exception;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public InternalServerException processException(Exception ex) {
        log.error("error ", ex);
        String message = ex.getMessage();
        InternalServerErrorDto errorDto = new InternalServerErrorDto(message);
        return new InternalServerException("Internal server error", Collections.singletonList(errorDto));
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", new Date());
        body.put("status", status.value());

        String message = getMessage("error.general");

        //Get all errors
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .map(this::getMessage)
                .collect(Collectors.toList());

        body.put("errors", errors);
        return new ResponseEntity<>(body, headers, status);
    }

    @Override
    protected ResponseEntity<Object> handleBindException(
            BindException bindException,
            HttpHeaders httpHeaders,
            HttpStatus httpStatus,
            WebRequest webRequest
    ) {
        List<ObjectError> errors = bindException.getAllErrors();

        List<BadRequestErrorDto> errorDtoList = errors
                .stream()
                .map(this::getDtoFromError)
                .sorted(Comparator.comparing(BadRequestErrorDto::getReason))
                .collect(Collectors.toList());

        return new ResponseEntity<>(errorDtoList, httpHeaders, httpStatus);
    }

    private BadRequestErrorDto getDtoFromError(ObjectError error) {
        String msg = error.getDefaultMessage();

        if (error instanceof FieldError) {
            String field = ((FieldError) error).getField();
            if (field.isEmpty()) {
                return new BadRequestErrorDto(MessageType.VALIDATION, ENTITY_NAME, msg);
            } else {
                return new BadRequestErrorDto(MessageType.VALIDATION, ENTITY_NAME + "." + field, msg);
            }
        } else {
            return new BadRequestErrorDto(MessageType.VALIDATION, ENTITY_NAME, msg);
        }
    }

    private String getMessage(String message) {
        Locale currentLocale = LocaleContextHolder.getLocale();
        try {
            return messageSource.getMessage(message, null, currentLocale);
        } catch (NoSuchMessageException e) {
            return message;
        }
    }

}