package com.vngrs.clinic.exception;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.vngrs.clinic.exception.badrequest.BadRequestException;
import com.vngrs.clinic.exception.forbidden.ForbiddenException;
import com.vngrs.clinic.exception.internalserver.InternalServerException;
import com.vngrs.clinic.exception.notfound.NotFoundException;
import com.vngrs.clinic.exception.unauthorized.UnauthorizedException;

@JsonIgnoreProperties({"cause", "localizedMessage", "stackTrace", "suppressed", "message"})

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME)
@JsonSubTypes({
        @JsonSubTypes.Type(value = BadRequestException.class, name = BadRequestException.TYPE_NAME),
        @JsonSubTypes.Type(value = InternalServerException.class, name = InternalServerException.TYPE_NAME),
        @JsonSubTypes.Type(value = UnauthorizedException.class, name = UnauthorizedException.TYPE_NAME),
        @JsonSubTypes.Type(value = NotFoundException.class, name = NotFoundException.TYPE_NAME),
        @JsonSubTypes.Type(value = ForbiddenException.class, name = ForbiddenException.TYPE_NAME)

})
public interface IBaseException {

}
