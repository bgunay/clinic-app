package com.vngrs.clinic.controller;

import com.vngrs.clinic.config.SwaggerConfiguration;
import com.vngrs.clinic.dto.request.PatientRequestDto;
import com.vngrs.clinic.dto.response.DoctorResponseDto;
import com.vngrs.clinic.dto.response.PatientResponseDto;
import com.vngrs.clinic.exception.forbidden.ForbiddenException;
import com.vngrs.clinic.exception.notfound.NotFoundException;
import com.vngrs.clinic.exception.unauthorized.UnauthorizedException;
import com.vngrs.clinic.service.PatientService;
import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Api(value = PatientController.ENDPOINT, tags = {SwaggerConfiguration.SMS_TAG})
@RequestMapping(value = PatientController.ENDPOINT)
public class PatientController {

    static final String ENDPOINT = "/patient";

    public final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @PostMapping
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = DoctorResponseDto.class),
            @ApiResponse(code = 401, message = "Patient not authorized", response = UnauthorizedException.class),
            @ApiResponse(code = 403, message = "Patient not allowed", response = ForbiddenException.class),
            @ApiResponse(code = 404, message = "Patient not found", response = NotFoundException.class)
    })
    public ResponseEntity<PatientResponseDto> createPatient(@Valid @RequestBody PatientRequestDto patientRequest) {
        PatientResponseDto patient = patientService.createPatient(patientRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(patient);
    }

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(
            value = "For getting Patient",
            nickname = "get Patient",
            notes = "You can get Patient by using this method"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = DoctorResponseDto.class),
            @ApiResponse(code = 401, message = "Patient not authorized", response = UnauthorizedException.class),
            @ApiResponse(code = 403, message = "Patient not allowed", response = ForbiddenException.class),
            @ApiResponse(code = 404, message = "Patient not found", response = NotFoundException.class)
    })
    public ResponseEntity<PatientResponseDto> getPatient(@ApiParam(value = "patientId", required = true) @RequestHeader("patientId") Long patientId) throws NotFoundException {
        PatientResponseDto dtoResponseDto = patientService.getPatient(patientId);
        return ResponseEntity.ok(dtoResponseDto);
    }

    @GetMapping("/list")
    public ResponseEntity<List<PatientResponseDto>> getPatientList() {
        List<PatientResponseDto> doctorList = patientService.getPatientList();
        return ResponseEntity.ok(doctorList);
    }
}
