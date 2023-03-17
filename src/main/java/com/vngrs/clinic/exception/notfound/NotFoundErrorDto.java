package com.vngrs.clinic.exception.notfound;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NotFoundErrorDto implements Serializable {

    private static final long serialVersionUID = -7328028369837136094L;

    private String entityName;

    private String id;
}
