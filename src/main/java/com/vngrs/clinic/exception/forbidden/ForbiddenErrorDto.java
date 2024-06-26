package com.vngrs.clinic.exception.forbidden;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ForbiddenErrorDto implements Serializable {

    private static final long serialVersionUID = -986501815379430743L;

    private String entityName;

    private String id;

    private String reason;

    private String path;
}
