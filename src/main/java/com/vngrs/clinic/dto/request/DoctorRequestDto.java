package com.vngrs.clinic.dto.request;

import lombok.*;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DoctorRequestDto {

    @NotEmpty(message = "error.doctor.name.empty")
    @Size(min = 1, max = 10000, message = "error.size")
    private String name;

    @NotEmpty(message = "error.doctor.surname.empty")
    @Size(min = 1, max = 10000, message = "{error.size}")
    private String surname;

    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal perHourFee;
}