package com.vngrs.clinic.controller;

import com.vngrs.clinic.dto.response.PatientResponseDto;
import com.vngrs.clinic.model.Patient;
import com.vngrs.clinic.service.PatientService;
import com.vngrs.clinic.service.PatientService;
import com.vngrs.clinic.util.JsonUtil;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = PatientController.class)
@AutoConfigureMockMvc
class PatientControllerIntegrationTest {

    @MockBean
    private PatientService service;

    @Autowired
    PatientController patientController;

    @Autowired
    private MockMvc mockMvc;



    @Test
    public void whenPatientControllerInjected_thenNotNull() throws Exception {
        assertThat(patientController).isNotNull();
    }

    @Test
    public void whenGetRequestToPatients_thenCorrectResponse() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/patient/list")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }


    @Test
    public void whenPostRequestToPatientsAndInValidPatients_thenBadRequestResponse() throws Exception {
        String patient = "{\"name\": \"burhan\"}";
        mockMvc.perform(MockMvcRequestBuilders.post("/patient")
                        .content(patient)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors", Matchers.hasSize(1)))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }


    @Test
    public void whenPostPatients_thenCreatePatients() throws Exception {
        PatientResponseDto alex = new PatientResponseDto(12L, "alex", "patient");

        given(service.createPatient(any())).willReturn(alex);

        mockMvc.perform(post("/patient").contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(alex)))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is("alex")));
        verify(service, VerificationModeFactory.times(1)).createPatient(any());
        reset(service);
    }
}