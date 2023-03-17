package com.vngrs.clinic.dto.request;

import lombok.*;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UpdateDoctorFeeDto {
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal perHourFee;
}