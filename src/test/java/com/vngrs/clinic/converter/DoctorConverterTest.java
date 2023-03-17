package com.vngrs.clinic.converter;

import com.vngrs.clinic.dto.request.DoctorRequestDto;
import com.vngrs.clinic.dto.response.DoctorResponseDto;
import com.vngrs.clinic.model.Doctor;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class DoctorConverterTest {

    @Test
    public void testConvertDoctorRequestDto() {
        DoctorRequestDto requestDto = new DoctorRequestDto();
        requestDto.setName("John");
        requestDto.setSurname("Doe");
        requestDto.setPerHourFee(BigDecimal.valueOf(100.0));

        Doctor doctor = DoctorConverter.convert(requestDto);

        assertNotNull(doctor);
        assertEquals("John", doctor.getName());
        assertEquals("Doe", doctor.getSurname());
        assertEquals(BigDecimal.valueOf(100.0), doctor.getPayHourFee());
    }

    @Test
    public void testConvertDoctor() {
        Doctor doctor = new Doctor();
        doctor.setId(1L);
        doctor.setName("Jane Smith");
        doctor.setSurname("Doe");
        doctor.setPayHourFee(BigDecimal.valueOf(150.0));

        DoctorResponseDto responseDto = DoctorConverter.convert(doctor);

        assertNotNull(responseDto);
        assertEquals(1L, responseDto.getId());
        assertEquals("Jane Smith", responseDto.getName());
        assertEquals("Doe", responseDto.getSurname());
        assertEquals(BigDecimal.valueOf(150.0), responseDto.getPerHourFee());
    }

    @Test
    public void testToResponseDtoList() {
        List<Doctor> doctors = new ArrayList<>();
        Doctor doctor1 = new Doctor();
        doctor1.setId(1L);
        doctor1.setName("John Doe");
        doctor1.setPayHourFee(BigDecimal.valueOf(100.0));
        doctors.add(doctor1);

        Doctor doctor2 = new Doctor();
        doctor2.setId(2L);
        doctor2.setName("Jane Smith");
        doctor2.setPayHourFee(BigDecimal.valueOf(150.0));
        doctors.add(doctor2);

        List<DoctorResponseDto> responseDtoList = DoctorConverter.toResponseDtoList(doctors);

        assertNotNull(responseDtoList);
        assertEquals(2, responseDtoList.size());

        DoctorResponseDto responseDto1 = responseDtoList.get(0);
        assertNotNull(responseDto1);
        assertEquals(1L, responseDto1.getId());
        assertEquals("John Doe", responseDto1.getName());
        assertEquals(BigDecimal.valueOf(100.0), responseDto1.getPerHourFee());

        DoctorResponseDto responseDto2 = responseDtoList.get(1);
        assertNotNull(responseDto2);
        assertEquals(2L, responseDto2.getId());
        assertEquals("Jane Smith", responseDto2.getName());
        assertEquals(BigDecimal.valueOf(150.0), responseDto2.getPerHourFee());
    }
}
