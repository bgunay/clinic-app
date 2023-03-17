package com.vngrs.clinic.handler;

import com.vngrs.clinic.exception.badrequest.BadRequestErrorDto;
import com.vngrs.clinic.exception.badrequest.BadRequestException;
import com.vngrs.clinic.exception.badrequest.MessageType;
import com.vngrs.clinic.exception.notfound.NotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.context.MessageSource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
public class ControllerExceptionHandlerTest {

    private static final String ENTITY_NAME = "clinic";
    private static final String GENERAL_ERROR_MESSAGE = "error.general";
    private static final String FIELD_ERROR_MESSAGE = "Field error message";
    private static final String VALIDATION_ERROR_MESSAGE = "Validation error message";

    private static final List<ObjectError> FIELD_ERRORS = Collections.singletonList(
            new FieldError(ENTITY_NAME, "fieldName", FIELD_ERROR_MESSAGE));

    private static final List<ObjectError> GLOBAL_ERRORS = Collections.singletonList(
            new ObjectError(ENTITY_NAME, VALIDATION_ERROR_MESSAGE));


    @Mock
    private MessageSource messageSource;

    @InjectMocks
    private ControllerExceptionHandler controllerExceptionHandler;

    @Test
    public void testProcessBadRequestException() {
        List<BadRequestErrorDto> errors = Collections.singletonList(new BadRequestErrorDto(MessageType.VALIDATION, VALIDATION_ERROR_MESSAGE, "fieldName"));
        BadRequestException exception = new BadRequestException("Bad request exception message", errors);

        BadRequestException result = controllerExceptionHandler.processBadRequestException(exception);

        assertEquals(exception, result);
    }

    @Test
    public void testProcessNotFoundException() {
        NotFoundException exception = new NotFoundException("Not found exception message");

        NotFoundException result = controllerExceptionHandler.processBadRequestException(exception);

        assertEquals(exception, result);
    }
}