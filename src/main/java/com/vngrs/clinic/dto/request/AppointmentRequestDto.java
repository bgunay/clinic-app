package com.vngrs.clinic.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AppointmentRequestDto {

    @NotEmpty(message = "error.doctorId.empty")
    @Size(min = 1, max = 1000000, message = "error.size")
    private Long doctorId;

    @FutureOrPresent(message = "error.endDate.past")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endDate;

    @NotNull(message = "error.patientId.empty")
    @Size(min = 1, max = 100000, message = "error.size")
    private Long patientId;

    @FutureOrPresent(message = "error.startDate.past")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startDate;

}