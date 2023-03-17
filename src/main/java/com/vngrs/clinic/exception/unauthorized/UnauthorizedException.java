package com.vngrs.clinic.exception.unauthorized;


import com.vngrs.clinic.exception.BaseException;
import lombok.Getter;
import lombok.Setter;

import java.util.Collections;
import java.util.List;

@Getter
@Setter
public class UnauthorizedException extends BaseException {

    public static final String TYPE_NAME = "UnauthorizedException";

    private static final long serialVersionUID = 426581278886813848L;

    private boolean captchaRequired;

    private List<UnauthorizedErrorDto> errors;

    @Deprecated
    public UnauthorizedException(String name, String description, List<UnauthorizedErrorDto> errors) {
        super(name, description);
        this.errors = errors;
    }

    public UnauthorizedException(String description, List<UnauthorizedErrorDto> errors) {
        super(TYPE_NAME, description);
        this.errors = errors;
    }

    public UnauthorizedException(String description, UnauthorizedErrorDto error) {
        super(TYPE_NAME, description);
        this.errors = Collections.singletonList(error);
    }


    public UnauthorizedException(String description) {
        super(TYPE_NAME, description);
        this.errors = Collections.emptyList();
    }

    public UnauthorizedException() {
        super();
    }

    @Override
    @Deprecated
    public void setName(String name) {
        super.setName(TYPE_NAME);
    }

}