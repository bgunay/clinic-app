package com.vngrs.clinic.exception;

import lombok.Getter;

@Getter
public class BaseException extends Exception implements IBaseException {

    private static final long serialVersionUID = 1756137890195469840L;

    private String name;
    private String description;

    public BaseException(String name, String description) {
        super(name + ": " + description);
        this.name = name;
        this.description = description;
    }

    public BaseException() {
        super();
    }

    protected BaseException(String name) {
        super();
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
