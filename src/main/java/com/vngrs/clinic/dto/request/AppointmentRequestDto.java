package com.vngrs.clinic.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentRequestDto {

    @NotEmpty(message = "error.doctorId.empty")
    @Size(min = 1, max = 1000000, message = "error.size")
    private Long doctorId;

    @FutureOrPresent(message = "error.endDate.past")
    private LocalDateTime endDate;

    @NotNull(message = "error.patientId.empty")
    @Size(min = 1, max = 100000, message = "error.size")
    private Long patientId;

    @FutureOrPresent(message = "error.startDate.past")
    private LocalDateTime startDate;
}