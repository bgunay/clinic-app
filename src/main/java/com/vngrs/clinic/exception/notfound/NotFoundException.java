package com.vngrs.clinic.exception.notfound;


import com.vngrs.clinic.exception.BaseException;
import lombok.Getter;
import lombok.Setter;

import java.util.Collections;
import java.util.List;

@Getter
@Setter
public class NotFoundException extends BaseException {

    private static final long serialVersionUID = 3754991262395176322L;

    public static final String TYPE_NAME = "NotFoundException";

    private List<NotFoundErrorDto> errors;

    @Deprecated
    public NotFoundException(String name, String description, List<NotFoundErrorDto> errors) {
        super(name, description);
        this.errors = errors;
    }

    @Deprecated
    public NotFoundException(String name, String description) {
        super(TYPE_NAME, description);
    }

    public NotFoundException(String description, List<NotFoundErrorDto> errors) {
        super(TYPE_NAME, description);
        this.errors = errors;
    }

    public NotFoundException(String description, NotFoundErrorDto error) {
        super(TYPE_NAME, description);
        this.errors = Collections.singletonList(error);
    }

    public NotFoundException(String description) {
        super(TYPE_NAME, description);
        this.errors = Collections.emptyList();
    }

    public NotFoundException() {
        super(TYPE_NAME);
    }

    @Override
    @Deprecated
    public void setName(String name) {
        super.setName(TYPE_NAME);
    }

}
