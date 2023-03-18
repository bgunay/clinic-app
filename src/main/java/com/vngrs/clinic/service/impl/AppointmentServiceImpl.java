package com.vngrs.clinic.service.impl;

import com.vngrs.clinic.converter.AppointmentConverter;
import com.vngrs.clinic.dto.enumeration.AppointmentStatus;
import com.vngrs.clinic.dto.request.AppointmentRequestDto;
import com.vngrs.clinic.dto.response.AppointmentResponseDto;
import com.vngrs.clinic.exception.forbidden.ForbiddenException;
import com.vngrs.clinic.exception.notfound.NotFoundException;
import com.vngrs.clinic.model.Appointment;
import com.vngrs.clinic.model.Doctor;
import com.vngrs.clinic.model.Patient;
import com.vngrs.clinic.repository.AppointmentRepository;
import com.vngrs.clinic.repository.DoctorRepository;
import com.vngrs.clinic.repository.PatientRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class AppointmentServiceImpl implements com.vngrs.clinic.service.AppointmentService {

    public static final int MIN_PER_HOUR = 60;
    public static final double CANCELLATION_RATIO = 0.25;

    public final AppointmentRepository appointmentRepository;
    public final DoctorRepository doctorRepository;
    public final PatientRepository patientRepository;


    @Override
    public List<AppointmentResponseDto> getUserAppointmentList(Long userId) {
        List<Appointment> appointmentList = appointmentRepository.findByPatient_Id(userId);
        List<AppointmentResponseDto> appointmentResponseDtoList = new ArrayList<>();
        for (Appointment appointment : appointmentList) {
            AppointmentResponseDto appointmentResponseDto = AppointmentConverter.convert(appointment);
            appointmentResponseDtoList.add(appointmentResponseDto);
        }

        return appointmentResponseDtoList;
    }

    @Override
    public AppointmentResponseDto cancelAppointment(Long appointmentId, Long userId) throws NotFoundException, ForbiddenException {
        Appointment appointment = appointmentRepository.findByIdAndPatient_Id(appointmentId, userId).orElseThrow(() -> new NotFoundException("Appointment not found"));
        if (appointment.getStatus() == AppointmentStatus.CANCELLED || appointment.getStatus() == AppointmentStatus.DONE) {
            throw new ForbiddenException("Appointment can not be cancelled");
        }
        BigDecimal cancellationFee = calculateCancellationFee(appointment.getStartDate(), appointment.getDoctor());
        appointment.setRefundedFee(cancellationFee);
        appointment.setStatus(AppointmentStatus.CANCELLED);
        Appointment savedAppointment = appointmentRepository.save(appointment);
        return AppointmentConverter.convert(savedAppointment);
    }

    @Override
    public AppointmentResponseDto createAppointment(AppointmentRequestDto appointmentCreateRequest) throws NotFoundException, ForbiddenException {
        Doctor doctor = doctorRepository.findById(appointmentCreateRequest.getDoctorId()).orElseThrow(() -> new NotFoundException("Doctor not found"));
        Patient patient = patientRepository.findById(appointmentCreateRequest.getPatientId()).orElseThrow(() -> new NotFoundException("Patient not found"));

        if (appointmentCreateRequest.getStartDate().isBefore(LocalDateTime.now()) || appointmentCreateRequest.getEndDate().isBefore(appointmentCreateRequest.getStartDate())) {
            throw new ForbiddenException("Appointment can not be created in the past");
        }

        Appointment appointment = new Appointment();
        BigDecimal totalFee = calculateAppointmentFee(appointmentCreateRequest.getStartDate(), appointmentCreateRequest.getEndDate(), doctor);
        appointment.setFee(totalFee);
        appointment.setStatus(AppointmentStatus.OPEN);
        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        appointment.setStartDate(appointmentCreateRequest.getStartDate());
        appointment.setEndDate(appointmentCreateRequest.getEndDate());

        Appointment savedAppointment = appointmentRepository.save(appointment);
        return AppointmentConverter.convert(savedAppointment);
    }

    public BigDecimal calculateAppointmentFee(LocalDateTime startDate, LocalDateTime endDate, Doctor doctor) {
        double diff = startDate.until(endDate, ChronoUnit.HOURS);
        return doctor.getPayHourFee().multiply(BigDecimal.valueOf(diff));
    }

    public BigDecimal calculateCancellationFee(LocalDateTime startDate, Doctor doctor) {
        LocalDateTime now = LocalDateTime.now();
        long timeToAppointment = now.until(startDate, ChronoUnit.MINUTES);
        if (timeToAppointment <= MIN_PER_HOUR) {
            return doctor.getPayHourFee().multiply(BigDecimal.valueOf(CANCELLATION_RATIO));
        } else {
            return BigDecimal.ZERO;
        }
    }

}
