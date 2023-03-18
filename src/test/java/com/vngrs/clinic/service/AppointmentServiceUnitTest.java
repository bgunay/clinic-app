package com.vngrs.clinic.service;

import com.vngrs.clinic.converter.DoctorConverter;
import com.vngrs.clinic.converter.PatientConverter;
import com.vngrs.clinic.dto.enumeration.AppointmentStatus;
import com.vngrs.clinic.dto.request.AppointmentRequestDto;
import com.vngrs.clinic.dto.response.AppointmentResponseDto;
import com.vngrs.clinic.dto.response.DoctorResponseDto;
import com.vngrs.clinic.dto.response.PatientResponseDto;
import com.vngrs.clinic.exception.forbidden.ForbiddenException;
import com.vngrs.clinic.exception.notfound.NotFoundException;
import com.vngrs.clinic.model.Appointment;
import com.vngrs.clinic.model.Doctor;
import com.vngrs.clinic.model.Patient;
import com.vngrs.clinic.repository.AppointmentRepository;
import com.vngrs.clinic.repository.DoctorRepository;
import com.vngrs.clinic.repository.PatientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith({MockitoExtension.class})
public class AppointmentServiceUnitTest {

    @Mock
    private AppointmentRepository appointmentRepository;

    @Mock
    private DoctorRepository doctorRepository;

    @Mock
    private PatientRepository patientRepository;

    @InjectMocks
    private AppointmentService appointmentService;

    private Appointment appointment;
    private Doctor doctor;
    private Patient patient;

    @BeforeEach
    void setUp() {
        doctor = new Doctor();
        doctor.setId(1L);
        doctor.setName("John Doe");
        doctor.setPayHourFee(BigDecimal.valueOf(100));

        patient = new Patient();
        patient.setId(2L);
        patient.setName("Jane");
        patient.setSurname("Doe2");

        LocalDateTime startDate = LocalDateTime.of(2023, 3, 17, 10, 0);
        LocalDateTime endDate = LocalDateTime.of(2023, 3, 17, 11, 0);

        appointment = new Appointment();
        appointment.setId(3L);
        appointment.setDoctor(doctor);
        appointment.setPatient(patient);
        appointment.setStartDate(startDate);
        appointment.setEndDate(endDate);
        appointment.setFee(BigDecimal.valueOf(100));
        appointment.setStatus(AppointmentStatus.OPEN);
    }

    @DisplayName("Test Should Pass When user get appointment list")
    @Test
    public void testGetUserAppointmentList() {
        Long userId = 2L;
        List<Appointment> appointmentList = new ArrayList<>();
        appointmentList.add(appointment);

        // Mock repository method
        when(appointmentRepository.findByPatient_Id(userId)).thenReturn(appointmentList);

        // Call service method
        List<AppointmentResponseDto> result = appointmentService.getUserAppointmentList(userId);

        // Verify results
        assertNotNull(result);
        assertEquals(1, result.size());

        AppointmentResponseDto appointmentResponseDto = result.get(0);
        assertNotNull(appointmentResponseDto);
        assertEquals(appointment.getId(), appointmentResponseDto.getId());
        assertEquals(appointment.getStartDate(), appointmentResponseDto.getStartDate());
        assertEquals(appointment.getEndDate(), appointmentResponseDto.getEndDate());
        assertEquals(appointment.getStatus(), appointmentResponseDto.getStatus());

        DoctorResponseDto doctorResponseDto = appointmentResponseDto.getDoctor();
        assertNotNull(doctorResponseDto);
        assertEquals(doctor.getId(), doctorResponseDto.getId());
        assertEquals(doctor.getName(), doctorResponseDto.getName());
        assertEquals(doctor.getSurname(), doctorResponseDto.getSurname());
        assertEquals(doctor.getPayHourFee(), doctorResponseDto.getPerHourFee());

        PatientResponseDto patientResponseDto = appointmentResponseDto.getPatient();
        assertNotNull(patientResponseDto);
        assertEquals(patient.getId(), patientResponseDto.getId());
        assertEquals(patient.getName(), patientResponseDto.getName());
        assertEquals(patient.getSurname(), patientResponseDto.getSurname());

        BigDecimal expectedFee = doctor.getPayHourFee().multiply(BigDecimal.valueOf(ChronoUnit.HOURS.between(appointment.getStartDate(), appointment.getEndDate())));
        assertEquals(expectedFee, appointmentResponseDto.getFee());
    }

    @Test
    public void testCreateAppointment() throws NotFoundException, ForbiddenException {
        // Arrange
        AppointmentRequestDto appointmentRequestDto = new AppointmentRequestDto();
        appointmentRequestDto.setDoctorId(1L);
        appointmentRequestDto.setPatientId(2L);
        appointmentRequestDto.setStartDate(LocalDateTime.of(2024, 3, 17, 10, 0));
        appointmentRequestDto.setEndDate(LocalDateTime.of(2024, 3, 17, 11, 0));

        Doctor doctor = new Doctor();
        doctor.setId(1L);
        doctor.setPayHourFee(BigDecimal.valueOf(100));

        Patient patient = new Patient();
        patient.setId(2L);

        Appointment appointment = new Appointment();
        appointment.setId(3L);
        appointment.setDoctor(doctor);
        appointment.setPatient(patient);
        appointment.setStatus(AppointmentStatus.OPEN);
        appointment.setStartDate(appointmentRequestDto.getStartDate());
        appointment.setFee(BigDecimal.valueOf(100));
        appointment.setEndDate(appointmentRequestDto.getEndDate());

        when(doctorRepository.findById(1L)).thenReturn(Optional.of(doctor));
        when(patientRepository.findById(2L)).thenReturn(Optional.of(patient));
        when(appointmentRepository.save(any(Appointment.class))).thenReturn(appointment);

        // Act
        AppointmentResponseDto appointmentResponseDto = appointmentService.createAppointment(appointmentRequestDto);

        // Assert
        verify(doctorRepository, times(1)).findById(1L);
        verify(patientRepository, times(1)).findById(2L);
        verify(appointmentRepository, times(1)).save(any(Appointment.class));
        assertNotNull(appointmentResponseDto);
        assertEquals(appointment.getId(), appointmentResponseDto.getId());
        assertEquals(appointmentRequestDto.getStartDate(), appointmentResponseDto.getStartDate());
        assertEquals(appointmentRequestDto.getEndDate(), appointmentResponseDto.getEndDate());
        assertEquals(AppointmentStatus.OPEN, appointmentResponseDto.getStatus());
        assertEquals(BigDecimal.valueOf(100), appointmentResponseDto.getFee());
        assertEquals(doctor.getId(), appointmentResponseDto.getDoctor().getId());
        assertEquals(patient.getId(), appointmentResponseDto.getPatient().getId());
    }

    @Test
    public void testCancelAppointment() throws NotFoundException, ForbiddenException {
        // Given
        Long appointmentId = 1L;
        Appointment appointment = new Appointment();
        appointment.setId(appointmentId);
        appointment.setStatus(AppointmentStatus.OPEN);
        appointment.setStartDate(LocalDateTime.now().plusHours(1));
        appointment.setEndDate(LocalDateTime.now().plusHours(2));
        appointment.setFee(BigDecimal.valueOf(100));
        Doctor doctor = new Doctor();
        doctor.setId(1L);
        doctor.setPayHourFee(BigDecimal.valueOf(100));
        appointment.setDoctor(doctor);
        appointment.setPatient(patient);
        when(appointmentRepository.findByIdAndPatient_Id(appointmentId, patient.getId())).thenReturn(Optional.of(appointment));
        when(appointmentRepository.save(appointment)).thenReturn(appointment);

        // When
        AppointmentResponseDto appointmentResponseDto = appointmentService.cancelAppointment(appointmentId, patient.getId());

        // Then
        assertEquals(appointment.getId(), appointmentResponseDto.getId());
        assertEquals(appointment.getStartDate(), appointmentResponseDto.getStartDate());
        assertEquals(appointment.getEndDate(), appointmentResponseDto.getEndDate());
        assertEquals(AppointmentStatus.CANCELLED, appointmentResponseDto.getStatus());
        assertEquals(DoctorConverter.convert(appointment.getDoctor()), appointmentResponseDto.getDoctor());
        assertEquals(PatientConverter.toResponseDto(appointment.getPatient()), appointmentResponseDto.getPatient());
    }

    @Test
    public void testCancelAppointmentNotFound() {
        // Given
        Long appointmentId = 1L;
        when(appointmentRepository.findByIdAndPatient_Id(appointmentId, patient.getId())).thenReturn(Optional.empty());

        // Then
        assertThrows(NotFoundException.class, () -> appointmentService.cancelAppointment(appointmentId, patient.getId()));
    }

    @Test
    public void testCalculateAppointmentFee() {
        // Arrange
        LocalDateTime startDate = LocalDateTime.of(2024, 3, 16, 10, 0);
        LocalDateTime endDate = LocalDateTime.of(2024, 3, 16, 12, 0);
        Doctor doctor = new Doctor();
        doctor.setPayHourFee(BigDecimal.valueOf(100));

        // Act
        BigDecimal actualFee = appointmentService.calculateAppointmentFee(startDate, endDate, doctor);

        // Assert
        assertEquals(0, BigDecimal.valueOf(200).compareTo(actualFee));
    }

    @Test
    public void testCalculateCancellationFee() {
        // Arrange
        LocalDateTime startDate = LocalDateTime.now().plusHours(1);
        Doctor doctor = new Doctor();
        doctor.setPayHourFee(BigDecimal.valueOf(100));

        // Act
        BigDecimal actualFee = appointmentService.calculateCancellationFee(startDate, doctor);

        // Assert
        assertEquals(25L, actualFee.longValue());
    }
}





