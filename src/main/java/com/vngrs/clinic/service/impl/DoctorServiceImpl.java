package com.vngrs.clinic.service.impl;

import com.vngrs.clinic.converter.DoctorConverter;
import com.vngrs.clinic.dto.request.DoctorRequestDto;
import com.vngrs.clinic.dto.request.UpdateDoctorFeeDto;
import com.vngrs.clinic.dto.response.DoctorResponseDto;
import com.vngrs.clinic.exception.notfound.NotFoundException;
import com.vngrs.clinic.model.Doctor;
import com.vngrs.clinic.repository.DoctorRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
@AllArgsConstructor
public class DoctorServiceImpl implements com.vngrs.clinic.service.DoctorService {

    public final DoctorRepository doctorRepository;

    @Override
    public DoctorResponseDto getDoctorById(Long id) throws NotFoundException {
        Doctor doctor = doctorRepository.findById(id).orElseThrow(() -> new NotFoundException("Doctor not found"));
        return DoctorConverter.convert(doctor);
    }

    @Override
    public DoctorResponseDto createDoctor(@RequestBody DoctorRequestDto doctor) {
        Doctor saveDoctor = doctorRepository.save(DoctorConverter.convert(doctor));
        return DoctorConverter.convert(saveDoctor);
    }

    @Override
    public DoctorResponseDto updateDoctor(@PathVariable Long id, @RequestBody UpdateDoctorFeeDto updateDoctorFeeDto) throws NotFoundException {
        Doctor doctor = doctorRepository.findById(id).orElseThrow(() -> new NotFoundException("Doctor not found"));
        doctor.setPayHourFee(updateDoctorFeeDto.getPerHourFee());
        Doctor updatedDoctor = doctorRepository.save(doctor);
        return DoctorConverter.convert(updatedDoctor);
    }

    @Override
    public void deleteDoctor(@PathVariable Long id) throws NotFoundException {
        Doctor doctor = doctorRepository.findById(id).orElseThrow(() -> new NotFoundException("Doctor not found"));
        doctorRepository.delete(doctor);
    }

    @Override
    public List<DoctorResponseDto> getDoctorList() {
        List<Doctor> all = doctorRepository.findAll();
        return DoctorConverter.toResponseDtoList(all);
    }
}
