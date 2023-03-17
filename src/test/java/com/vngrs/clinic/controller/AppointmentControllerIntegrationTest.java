package com.vngrs.clinic.controller;

import com.vngrs.clinic.dto.enumeration.AppointmentStatus;
import com.vngrs.clinic.dto.response.AppointmentResponseDto;
import com.vngrs.clinic.dto.response.DoctorResponseDto;
import com.vngrs.clinic.dto.response.PatientResponseDto;
import com.vngrs.clinic.service.AppointmentService;
import com.vngrs.clinic.util.JsonUtil;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
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

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = AppointmentController.class)
@AutoConfigureMockMvc
public class AppointmentControllerIntegrationTest {


    @MockBean
    private AppointmentService service;

    @Autowired
    AppointmentController appointmentController;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldFindAppointmentsStatusAndContentIsOK() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/appointment")
                        .header("userId", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }


    @Test
    public void createAppointmentList_validateHttpStatus() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/appointment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "    \"id\": 1,\n" +
                                "    \"firstName\": \"John\",\n" +
                                "    \"lastName\": \"Doe\",\n" +
                                "    \"fee\": 100.00\n" +
                                "}"))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void createAppointment_validateHttpStatusAndContentAndValues() throws Exception {
        AppointmentResponseDto appointmentResponseDto = new AppointmentResponseDto();
        appointmentResponseDto.setId(1L);
        appointmentResponseDto.setPatient(new PatientResponseDto());
        appointmentResponseDto.setDoctor(new DoctorResponseDto());
        appointmentResponseDto.setStatus(AppointmentStatus.OPEN);
        appointmentResponseDto.setFee(BigDecimal.TEN);

        given(service.createAppointment(Mockito.any())).willReturn(appointmentResponseDto);

        mockMvc.perform(post("/appointment").contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.toJson(appointmentResponseDto))).andExpect(status().isCreated()).
                andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.fee", is(10)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));

        verify(service, VerificationModeFactory.times(1)).createAppointment(Mockito.any());
        reset(service);

    }

}