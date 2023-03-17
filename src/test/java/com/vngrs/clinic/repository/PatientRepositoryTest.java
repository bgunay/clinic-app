package com.vngrs.clinic.repository;

import com.vngrs.clinic.model.Patient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class PatientRepositoryTest {


    @Autowired
    PatientRepository patientRepository;

    @Test
    public void shouldSaveUser_toMysql() {
        final Patient user = new Patient();
        user.setName("name");
        user.setSurname("surname");

        final Patient saved = patientRepository.save(user);

        assertNotNull(saved);
    }
}