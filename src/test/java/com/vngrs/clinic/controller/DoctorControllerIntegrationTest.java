package com.vngrs.clinic.controller;

import com.vngrs.clinic.dto.response.DoctorResponseDto;
import com.vngrs.clinic.service.DoctorService;
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

@WebMvcTest(controllers = DoctorController.class)
@AutoConfigureMockMvc
class DoctorControllerIntegrationTest {

    @MockBean
    private DoctorService service;

    @Autowired
    DoctorController doctorController;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void whenDoctorControllerInjected_thenNotNull() throws Exception {
        assertThat(doctorController).isNotNull();
    }

    @Test
    public void whenGetRequestToDoctors_thenCorrectResponse() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/doctor/list")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));

    }

    @Test
    public void whenPostRequestToDoctorsAndInValidDoctors_thenBadRequestResponse() throws Exception {
        Random randomNum = new Random();
        int randomInt = randomNum.nextInt(1000);
        String doctor = "{ \"surname\" : \"gunay\", \"perHourFee\" : " + randomInt + "}";
        mockMvc.perform(MockMvcRequestBuilders.post("/doctor")
                        .content(doctor)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors", Matchers.hasSize(1)))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }


    @Test
    public void whenPostDoctors_thenCreateDoctors() throws Exception {
        DoctorResponseDto alex = new DoctorResponseDto(12L, "alex", "surname", BigDecimal.TEN);

        given(service.createDoctor(any())).willReturn(alex);

        mockMvc.perform(post("/doctor").contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(alex)))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is("alex")));
        verify(service, VerificationModeFactory.times(1)).createDoctor(any());
        reset(service);
    }

}

