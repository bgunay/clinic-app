package com.vngrs.clinic.exception.internalserver;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InternalServerErrorDto implements Serializable {

    private static final long serialVersionUID = 4143152490720150370L;

    private String reason;
}
