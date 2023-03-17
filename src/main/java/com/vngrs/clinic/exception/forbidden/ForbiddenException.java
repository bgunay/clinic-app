package com.vngrs.clinic.exception.forbidden;

import com.vngrs.clinic.exception.BaseException;
import lombok.Getter;
import lombok.Setter;

import java.util.Collections;
import java.util.List;

@Getter
@Setter
public class ForbiddenException extends BaseException {

    private static final long serialVersionUID = -5703611402635494043L;

    public static final String TYPE_NAME = "ForbiddenException";

    private List<ForbiddenErrorDto> errors;

    @Deprecated
    public ForbiddenException(String name, String description, List<ForbiddenErrorDto> errors) {
        super(name, description);
        this.errors = errors;
    }

    public ForbiddenException(String description, List<ForbiddenErrorDto> errors) {
        super(TYPE_NAME, description);
        this.errors = errors;
    }

    public ForbiddenException(String description, ForbiddenErrorDto error) {
        super(TYPE_NAME, description);
        this.errors = Collections.singletonList(error);
    }


    public ForbiddenException(String description) {
        super(TYPE_NAME, description);
        this.errors = Collections.emptyList();
    }

    public ForbiddenException() {
        super(TYPE_NAME);
    }

    @Override
    @Deprecated
    public void setName(String name) {
        super.setName(TYPE_NAME);
    }

}
