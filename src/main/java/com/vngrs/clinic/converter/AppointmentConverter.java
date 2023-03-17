package com.vngrs.clinic.converter;

import com.vngrs.clinic.dto.enumeration.AppointmentStatus;
import com.vngrs.clinic.dto.response.AppointmentResponseDto;
import com.vngrs.clinic.model.Appointment;

public class AppointmentConverter {

    public static AppointmentResponseDto convert(Appointment appointment){
        AppointmentResponseDto appointmentResponseDto = new AppointmentResponseDto();
        appointmentResponseDto.setId(appointment.getId());
        appointmentResponseDto.setStartDate(appointment.getStartDate());
        appointmentResponseDto.setEndDate(appointment.getEndDate());
        appointmentResponseDto.setStatus(appointment.getStatus());
        appointmentResponseDto.setFee(appointment.getFee());
        appointmentResponseDto.setPatient(PatientConverter.toResponseDto(appointment.getPatient()));
        appointmentResponseDto.setDoctor(DoctorConverter.convert(appointment.getDoctor()));

        if (appointment.getStatus() == AppointmentStatus.CANCELLED) {
            appointmentResponseDto.setRefundedFee(appointment.getRefundedFee());
        }

        return appointmentResponseDto;
    }
}
