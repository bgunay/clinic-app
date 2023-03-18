package com.vngrs.clinic.controller;

import com.vngrs.clinic.config.SwaggerConfiguration;
import com.vngrs.clinic.dto.request.DoctorRequestDto;
import com.vngrs.clinic.dto.request.UpdateDoctorFeeDto;
import com.vngrs.clinic.dto.response.DoctorResponseDto;
import com.vngrs.clinic.exception.forbidden.ForbiddenException;
import com.vngrs.clinic.exception.notfound.NotFoundException;
import com.vngrs.clinic.exception.unauthorized.UnauthorizedException;
import com.vngrs.clinic.service.DoctorService;
import com.vngrs.clinic.service.impl.DoctorServiceImpl;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Api(value = DoctorController.ENDPOINT, tags = {SwaggerConfiguration.SMS_TAG})
@RequestMapping(value = DoctorController.ENDPOINT)
@Slf4j
public class DoctorController {

    static final String ENDPOINT = "/doctor";

    public final DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @GetMapping
    @ApiOperation(
            value = "For getting doctor",
            nickname = "get doctor",
            notes = "You can get Doctor by using this method"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = DoctorResponseDto.class),
            @ApiResponse(code = 401, message = "Doctor not authorized", response = UnauthorizedException.class),
            @ApiResponse(code = 403, message = "Doctor not allowed", response = ForbiddenException.class),
            @ApiResponse(code = 404, message = "Doctor not found", response = NotFoundException.class)
    })
    public ResponseEntity<DoctorResponseDto> getDoctor(@ApiParam(value = "doctorId", required = true) @RequestHeader("doctorId") Long doctorId) throws NotFoundException {
        DoctorResponseDto dtoResponseDto = doctorService.getDoctorById(doctorId);
        return ResponseEntity.ok(dtoResponseDto);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(
            value = "For creating doctor",
            nickname = "Create doctor",
            notes = "You can create new Doctor by using this method"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Doctor created", response = void.class),
            @ApiResponse(code = 401, message = "User not authorized", response = UnauthorizedException.class),
            @ApiResponse(code = 403, message = "User not allowed", response = ForbiddenException.class),
            @ApiResponse(code = 404, message = "Doctor not found", response = NotFoundException.class)
    })
    public ResponseEntity<DoctorResponseDto> createDoctor(@Valid @RequestBody DoctorRequestDto doctorRequestDto) {
        DoctorResponseDto doctorResponseDto = doctorService.createDoctor(doctorRequestDto);
        return new ResponseEntity<>(doctorResponseDto, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Doctor created", response = void.class),
            @ApiResponse(code = 401, message = "User not authorized", response = UnauthorizedException.class),
            @ApiResponse(code = 403, message = "User not allowed", response = ForbiddenException.class),
            @ApiResponse(code = 404, message = "Doctor not found", response = NotFoundException.class)
    })
    public ResponseEntity<DoctorResponseDto> updateDoctor(@PathVariable Long id, @Valid @RequestBody UpdateDoctorFeeDto updateDoctorFeeDto) throws NotFoundException {
        DoctorResponseDto doctorResponseDto = doctorService.updateDoctor(id, updateDoctorFeeDto);
        return new ResponseEntity<>(doctorResponseDto, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteDoctor(@PathVariable Long id) throws NotFoundException {
        doctorService.deleteDoctor(id);
        return ResponseEntity.ok(true);
    }

    @GetMapping("/list")
    @ApiResponses(value = {
            @ApiResponse(code = 401, message = "User not authorized", response = UnauthorizedException.class),
            @ApiResponse(code = 403, message = "User not allowed", response = ForbiddenException.class),
            @ApiResponse(code = 404, message = "Doctor not found", response = NotFoundException.class)
    })
    public ResponseEntity<List<DoctorResponseDto>> getDoctorList() {
        List<DoctorResponseDto> doctorList = doctorService.getDoctorList();
        return ResponseEntity.ok(doctorList);
    }

}