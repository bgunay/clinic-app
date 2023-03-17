package com.vngrs.clinic.exception.unauthorized;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UnauthorizedErrorDto implements Serializable {

    private static final long serialVersionUID = -4404278438851659916L;

    private String reason;

    private String entityName;

    private String path;

}
