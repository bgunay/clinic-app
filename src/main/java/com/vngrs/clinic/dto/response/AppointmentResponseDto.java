package com.vngrs.clinic.dto.response;

import com.vngrs.clinic.dto.enumeration.AppointmentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentResponseDto {
    private Long id;
    private BigDecimal fee;
    private DoctorResponseDto doctor;
    private PatientResponseDto patient;
    private BigDecimal refundedFee;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private AppointmentStatus status;

}