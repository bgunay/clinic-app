package com.vngrs.clinic.converter;

import com.vngrs.clinic.dto.request.DoctorRequestDto;
import com.vngrs.clinic.dto.response.DoctorResponseDto;
import com.vngrs.clinic.model.Doctor;

import java.util.List;
import java.util.stream.Collectors;

public class DoctorConverter {

    public static Doctor convert(DoctorRequestDto doctorRequestDto) {
        Doctor doctor = new Doctor();
        doctor.setName(doctorRequestDto.getName());
        doctor.setSurname(doctorRequestDto.getSurname());
        doctor.setPayHourFee(doctorRequestDto.getPerHourFee());
        return doctor;

    }

    public static DoctorResponseDto convert(Doctor doctor) {
        return DoctorResponseDto.builder()
                .id(doctor.getId())
                .name(doctor.getName())
                .surname(doctor.getSurname())
                .perHourFee(doctor.getPayHourFee()).build();
    }

    public static List<DoctorResponseDto> toResponseDtoList(List<Doctor> doctors) {
        return doctors.stream().map(DoctorConverter::convert).collect(Collectors.toList());
    }

}
