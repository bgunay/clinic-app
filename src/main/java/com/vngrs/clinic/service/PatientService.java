package com.vngrs.clinic.service;

import com.vngrs.clinic.dto.request.PatientRequestDto;
import com.vngrs.clinic.dto.response.PatientResponseDto;
import com.vngrs.clinic.exception.notfound.NotFoundException;

import java.util.List;

public interface PatientService {
    PatientResponseDto createPatient(PatientRequestDto patientRequest);

    PatientResponseDto getPatient(Long id) throws NotFoundException;

    List<PatientResponseDto> getPatientList();
}
