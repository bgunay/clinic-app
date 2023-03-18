package com.vngrs.clinic.repository;

import com.vngrs.clinic.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByPatient_Id(Long userId);

    Optional<Appointment> findByIdAndPatient_Id(Long doctorId, Long patientId);
}
