package com.vngrs.clinic.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.DecimalMin;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UpdateDoctorFeeDto {
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal perHourFee;
}