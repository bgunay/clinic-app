package com.vngrs.clinic.exception.badrequest;

import com.vngrs.clinic.exception.BaseException;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Collections;
import java.util.List;

@Getter
@Setter
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class BadRequestException extends BaseException {

    private static final long serialVersionUID = 8039762057718393751L;

    public static final String TYPE_NAME = "BadRequestException";

    private List<BadRequestErrorDto> errors;


    public BadRequestException(String description, List<BadRequestErrorDto> errors) {
        super(TYPE_NAME, description);
        this.errors = errors;
    }

    public BadRequestException(String description, BadRequestErrorDto error) {
        super(TYPE_NAME, description);
        this.errors = Collections.singletonList(error);
    }


    public BadRequestException() {
        super(TYPE_NAME);
    }

    @Override
    public void setName(String name) {
        super.setName(TYPE_NAME);
    }

}
