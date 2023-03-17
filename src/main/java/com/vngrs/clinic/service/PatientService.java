package com.vngrs.clinic.service;

import com.vngrs.clinic.converter.PatientConverter;
import com.vngrs.clinic.dto.request.PatientRequestDto;
import com.vngrs.clinic.dto.response.PatientResponseDto;
import com.vngrs.clinic.exception.notfound.NotFoundException;
import com.vngrs.clinic.model.Patient;
import com.vngrs.clinic.repository.PatientRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PatientService {

    public final PatientRepository patientRepository;


    public PatientResponseDto createPatient(PatientRequestDto patientRequest) {
        Patient patient = PatientConverter.toEntity(patientRequest);
        patientRepository.save(patient);
        return PatientConverter.toResponseDto(patient);
    }

    public PatientResponseDto getPatient(Long id) throws NotFoundException {
        Patient patient = patientRepository.findById(id).orElseThrow(() -> new NotFoundException("Patient not found"));
        return PatientConverter.toResponseDto(patient);

    }

    public List<PatientResponseDto> getPatientList() {
        List<Patient> patientList = patientRepository.findAll();
        return PatientConverter.toResponseDtoList(patientList);
    }
}
