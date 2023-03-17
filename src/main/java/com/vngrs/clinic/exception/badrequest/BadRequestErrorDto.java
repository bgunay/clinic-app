package com.vngrs.clinic.exception.badrequest;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BadRequestErrorDto implements Serializable {

    private static final long serialVersionUID = 7094155124684806606L;

    private MessageType type;

    private String source;

    private String reason;
}
