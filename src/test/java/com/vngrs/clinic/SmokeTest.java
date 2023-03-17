package com.vngrs.clinic;


import static org.assertj.core.api.Assertions.assertThat;

import com.vngrs.clinic.controller.AppointmentController;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SmokeTest {

    @Autowired
    private AppointmentController controller;

    @Test
    public void whenSpringContextIsBootstrapped_thenNoExceptions() throws Exception {
        assertThat(controller).isNotNull();
    }
}