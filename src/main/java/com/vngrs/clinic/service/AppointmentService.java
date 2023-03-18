package com.vngrs.clinic.service;

import com.vngrs.clinic.dto.request.AppointmentRequestDto;
import com.vngrs.clinic.dto.response.AppointmentResponseDto;
import com.vngrs.clinic.exception.forbidden.ForbiddenException;
import com.vngrs.clinic.exception.notfound.NotFoundException;

import java.util.List;

public interface AppointmentService {
    List<AppointmentResponseDto> getUserAppointmentList(Long userId);

    AppointmentResponseDto cancelAppointment(Long appointmentId, Long userId) throws NotFoundException, ForbiddenException;

    AppointmentResponseDto createAppointment(AppointmentRequestDto appointmentCreateRequest) throws NotFoundException, ForbiddenException;
}
