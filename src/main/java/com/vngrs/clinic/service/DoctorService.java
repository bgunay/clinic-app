package com.vngrs.clinic.service;

import com.vngrs.clinic.dto.request.DoctorRequestDto;
import com.vngrs.clinic.dto.request.UpdateDoctorFeeDto;
import com.vngrs.clinic.dto.response.DoctorResponseDto;
import com.vngrs.clinic.exception.notfound.NotFoundException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface DoctorService {
    DoctorResponseDto getDoctorById(Long id) throws NotFoundException;

    DoctorResponseDto createDoctor(@RequestBody DoctorRequestDto doctor);

    DoctorResponseDto updateDoctor(@PathVariable Long id, @RequestBody UpdateDoctorFeeDto updateDoctorFeeDto) throws NotFoundException;

    void deleteDoctor(@PathVariable Long id) throws NotFoundException;

    List<DoctorResponseDto> getDoctorList();
}
