package com.vngrs.clinic.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PatientRequestDto {
    private Long id;
    @NotEmpty(message = "error.patient.name.empty")
    @Size(min = 1, max = 10000, message = "error.size")
    private String name;
    @NotEmpty(message = "error.patient.surname.empty")
    @Size(min = 1, max = 10000, message = "error.size")
    private String surname;
}