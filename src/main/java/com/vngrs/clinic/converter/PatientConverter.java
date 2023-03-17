package com.vngrs.clinic.converter;

import com.vngrs.clinic.dto.request.PatientRequestDto;
import com.vngrs.clinic.dto.response.PatientResponseDto;
import com.vngrs.clinic.model.Patient;

import java.util.List;
import java.util.stream.Collectors;

public class PatientConverter {

    public static Patient toEntity(PatientRequestDto patientRequestDto) {
        Patient patient = new Patient();
        patient.setName(patientRequestDto.getName());
        patient.setSurname(patientRequestDto.getSurname());
        if (patientRequestDto.getId() != null)
            patient.setId(patientRequestDto.getId());

        return patient;

    }

    public static PatientResponseDto toResponseDto(Patient patient) {
        PatientResponseDto patientResponseDto = PatientResponseDto.builder()
                .name(patient.getName())
                .surname(patient.getSurname()).build();
        if (patient.getId() != null)
            patientResponseDto.setId(patient.getId());
        return patientResponseDto;
    }

    public static List<PatientResponseDto> toResponseDtoList(List<Patient> patientList) {
        return patientList.stream().map(PatientConverter::toResponseDto).collect(Collectors.toList());
    }
}
